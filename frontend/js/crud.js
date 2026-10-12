// Motor de CRUD generico: cada tela e descrita por uma config (ver entities.js)
//
// config = {
//   title, subtitle, icon, endpoint,
//   columns:  [{ label, render(item) -> html }],
//   searches: [{ key, label, path, params: [{ name, placeholder, type }] }],
//   fields:   [{ name, label, type, ... }]   // text | email | number | select | checks | group | list
//   get(item) -> valores iniciais do formulario (no formato do payload)
// }

// ---------- formulario ----------

// carrega as opcoes dos selects / checkboxes (inclusive dos campos aninhados)
async function loadOptions(fields, opts) {
  for (const f of fields) {
    if (f.options) opts[f.name] = typeof f.options === 'function' ? await f.options() : f.options;
    if (f.fields) await loadOptions(f.fields, opts);
  }
  return opts;
}

function inputHtml(f, value, attr, opts) {
  const required = f.required === false ? '' : 'required';
  const common = attr + '="' + f.name + '" ' + required +
    (f.maxlength ? ' maxlength="' + f.maxlength + '"' : '') +
    (f.pattern ? ' pattern="' + f.pattern + '"' : '') +
    (f.placeholder ? ' placeholder="' + esc(f.placeholder) + '"' : '');

  if (f.type === 'select') {
    const options = (opts[f.name] || []).map((o) =>
      '<option value="' + esc(o.value) + '"' + (String(o.value) === String(value ?? '') ? ' selected' : '') + '>' +
      esc(o.label) + '</option>').join('');
    return '<select class="form-select" ' + common + '><option value="">Selecione...</option>' + options + '</select>';
  }

  const type = f.type === 'number' ? 'number' : (f.type === 'email' ? 'email' : 'text');
  const extra = f.type === 'number'
    ? (' step="' + (f.step || 1) + '"' + (f.min !== undefined ? ' min="' + f.min + '"' : '') +
       (f.max !== undefined ? ' max="' + f.max + '"' : ''))
    : '';
  const listId = f.suggestions ? 'dl_' + f.name : '';
  const datalist = f.suggestions
    ? '<datalist id="' + listId + '">' + f.suggestions.map((s) => '<option value="' + esc(s) + '">').join('') + '</datalist>'
    : '';
  return '<input class="form-control" type="' + type + '" ' + common + extra +
    (listId ? ' list="' + listId + '"' : '') + ' value="' + esc(value) + '">' + datalist;
}

function scalarFieldHtml(f, value, attr, opts) {
  return '<div class="col-md-' + (f.col || 6) + '"><label class="form-label">' + esc(f.label) + '</label>' +
    inputHtml(f, value, attr, opts) + '<div class="invalid-feedback"></div></div>';
}

function listItemHtml(f, value, opts) {
  const cols = f.fields.map((sf) => scalarFieldHtml(sf, value ? value[sf.name] : '', 'data-sub', opts)).join('');
  return '<div class="list-item"><div class="row g-3">' + cols + '</div>' +
    '<div class="text-end mt-2"><button type="button" class="btn btn-sm btn-outline-danger remove-item">' +
    '<i class="bi bi-trash"></i> Remover</button></div></div>';
}

function fieldHtml(f, value, opts) {
  if (f.type === 'group') {
    const cols = f.fields.map((sf) => scalarFieldHtml(sf, value ? value[sf.name] : '', 'data-sub', opts)).join('');
    return '<div class="col-12"><fieldset class="subform" data-group="' + f.name + '"><legend>' + esc(f.label) +
      '</legend><div class="row g-3 mt-0">' + cols + '</div></fieldset></div>';
  }
  if (f.type === 'list') {
    const items = (value && value.length ? value : [null]).map((v) => listItemHtml(f, v, opts)).join('');
    return '<div class="col-12"><fieldset class="subform" data-list="' + f.name + '"><legend>' + esc(f.label) +
      '</legend><div class="list-items mt-2">' + items + '</div>' +
      '<button type="button" class="btn btn-sm btn-outline-secondary add-item"><i class="bi bi-plus-lg"></i> ' +
      esc(f.addLabel || 'Adicionar') + '</button></fieldset></div>';
  }
  if (f.type === 'checks') {
    const selected = (value || []).map(String);
    const boxes = (opts[f.name] || []).map((o) =>
      '<div class="form-check"><input class="form-check-input" type="checkbox" id="chk_' + f.name + '_' + esc(o.value) +
      '" value="' + esc(o.value) + '"' + (selected.includes(String(o.value)) ? ' checked' : '') + '>' +
      '<label class="form-check-label" for="chk_' + f.name + '_' + esc(o.value) + '">' + esc(o.label) + '</label></div>'
    ).join('');
    return '<div class="col-12"><label class="form-label">' + esc(f.label) + '</label>' +
      '<div class="checks" data-checks="' + f.name + '">' + boxes + '</div></div>';
  }
  return scalarFieldHtml(f, value, 'data-name', opts);
}

function readScalar(f, el) {
  let v = el.value.trim();
  if (f.type === 'number' || f.numeric) return v === '' ? null : Number(v);
  if (v === '' && f.required === false) return null;
  return v;
}

// le o formulario de volta para o formato do payload da API
function collect(root, fields, attr) {
  const out = {};
  for (const f of fields) {
    if (f.type === 'group') {
      out[f.name] = collect(root.querySelector('[data-group="' + f.name + '"]'), f.fields, 'data-sub');
    } else if (f.type === 'list') {
      const box = root.querySelector('[data-list="' + f.name + '"]');
      out[f.name] = [...box.querySelectorAll('.list-item')].map((it) => collect(it, f.fields, 'data-sub'));
    } else if (f.type === 'checks') {
      out[f.name] = [...root.querySelectorAll('[data-checks="' + f.name + '"] input:checked')].map((i) => Number(i.value));
    } else {
      out[f.name] = readScalar(f, root.querySelector('[' + attr + '="' + f.name + '"]'));
    }
  }
  return out;
}

function showFormError(error) {
  const box = document.getElementById('formError');
  let html = '<strong>' + esc(error.message) + '</strong>';
  if (error.violations && error.violations.length) {
    html += '<ul class="mb-0 mt-1">' +
      error.violations.map((v) => '<li>' + esc(v.field) + ': ' + esc(v.message) + '</li>').join('') + '</ul>';
  }
  box.innerHTML = html;
  box.classList.remove('d-none');
}

async function openForm(cfg, item, onSaved) {
  const formEl = document.getElementById('crudForm');
  const errorBox = document.getElementById('formError');
  const saveBtn = document.getElementById('btnSalvar');
  errorBox.classList.add('d-none');
  document.getElementById('formModalTitle').textContent = (item ? 'Editar ' : 'Novo(a) ') + cfg.singular;

  let opts;
  try {
    opts = await loadOptions(cfg.fields, {});
  } catch (e) {
    toast(e.message, 'danger');
    return;
  }

  const values = item ? cfg.get(item) : (cfg.defaults || {});
  formEl.innerHTML = '<div class="row g-3">' + cfg.fields.map((f) => fieldHtml(f, values[f.name], opts)).join('') + '</div>';

  // listas dinamicas (ex.: enderecos do cliente)
  formEl.querySelectorAll('[data-list]').forEach((box) => {
    const f = cfg.fields.find((x) => x.name === box.dataset.list);
    box.addEventListener('click', (ev) => {
      if (ev.target.closest('.add-item')) {
        box.querySelector('.list-items').insertAdjacentHTML('beforeend', listItemHtml(f, null, opts));
      } else if (ev.target.closest('.remove-item')) {
        const items = box.querySelectorAll('.list-item');
        if (items.length > 1) ev.target.closest('.list-item').remove();
        else toast('Mantenha ao menos um item.', 'warning');
      }
    });
  });

  const modal = bootstrap.Modal.getOrCreateInstance(document.getElementById('formModal'));

  formEl.onsubmit = async (ev) => {
    ev.preventDefault();
    formEl.classList.add('was-validated');
    if (!formEl.checkValidity()) {
      formEl.reportValidity();
      return;
    }
    saveBtn.disabled = true;
    try {
      const payload = collect(formEl, cfg.fields, 'data-name');
      if (item) await api('PUT', cfg.endpoint + '/' + item.id, payload);
      else await api('POST', cfg.endpoint, payload);
      modal.hide();
      toast(cfg.singular + (item ? ' atualizado(a)' : ' cadastrado(a)') + ' com sucesso.');
      onSaved();
    } catch (e) {
      showFormError(e);
    } finally {
      saveBtn.disabled = false;
    }
  };

  formEl.classList.remove('was-validated');
  modal.show();
}

// ---------- tela de listagem ----------

async function renderCrud(cfg) {
  const app = document.getElementById('app');
  const searches = cfg.searches || [];
  let current = null; // busca ativa {path, query}
  let items = [];

  app.innerHTML =
    '<div class="page-header d-flex flex-wrap justify-content-between align-items-center gap-2 mb-3">' +
      '<div><h2 class="mb-0"><i class="bi ' + cfg.icon + ' me-2 accent"></i>' + esc(cfg.title) + '</h2>' +
      '<p class="text-muted mb-0">' + esc(cfg.subtitle) + '</p></div>' +
      '<button class="btn btn-accent" id="btnNovo"><i class="bi bi-plus-lg"></i> Novo(a) ' + esc(cfg.singular) + '</button>' +
    '</div>' +
    (searches.length
      ? '<div class="card shadow-sm mb-3"><div class="card-body"><form id="searchForm" class="row g-2 align-items-end"></form></div></div>'
      : '') +
    '<div class="card shadow-sm"><div class="table-responsive"><table class="table table-hover align-middle mb-0">' +
      '<thead><tr>' + cfg.columns.map((c) => '<th>' + esc(c.label) + '</th>').join('') + '<th class="text-end">Ações</th></tr></thead>' +
      '<tbody id="tbody"></tbody></table></div></div>';

  const tbody = document.getElementById('tbody');

  function renderRows() {
    if (!items.length) {
      tbody.innerHTML = '<tr><td colspan="' + (cfg.columns.length + 1) + '"><div class="empty-state">' +
        '<i class="bi bi-inbox fs-1 d-block mb-2"></i>Nenhum registro encontrado.</div></td></tr>';
      return;
    }
    tbody.innerHTML = items.map((item) =>
      '<tr>' + cfg.columns.map((c) => '<td>' + c.render(item) + '</td>').join('') +
      '<td class="text-end text-nowrap">' +
        '<button class="btn btn-sm btn-outline-secondary me-1" data-edit="' + item.id + '" title="Editar"><i class="bi bi-pencil"></i></button>' +
        '<button class="btn btn-sm btn-outline-danger" data-del="' + item.id + '" title="Excluir"><i class="bi bi-trash"></i></button>' +
      '</td></tr>').join('');
  }

  async function load() {
    let path = cfg.endpoint;
    if (current) path = current.path + '?' + current.query;
    try {
      items = await api('GET', path);
    } catch (e) {
      items = [];
      toast(e.message, 'danger');
    }
    renderRows();
  }

  // formulario de busca (varia conforme o tipo de busca escolhido)
  if (searches.length) {
    const form = document.getElementById('searchForm');
    const drawInputs = (search) => {
      form.querySelector('#searchInputs').innerHTML = search.params.map((p) =>
        '<div class="col"><label class="form-label small mb-1">' + esc(p.label || p.name) + '</label>' +
        '<input class="form-control" data-param="' + p.name + '" type="' + (p.type || 'text') + '"' +
        (p.type === 'number' ? ' step="0.01"' : '') + ' placeholder="' + esc(p.placeholder || '') + '"></div>').join('');
    };
    form.innerHTML =
      (searches.length > 1
        ? '<div class="col-md-3"><label class="form-label small mb-1">Buscar por</label>' +
          '<select class="form-select" id="searchType">' +
          searches.map((s, i) => '<option value="' + i + '">' + esc(s.label) + '</option>').join('') + '</select></div>'
        : '') +
      '<div class="col-md"><div class="row g-2" id="searchInputs"></div></div>' +
      '<div class="col-md-auto d-flex gap-2">' +
        '<button class="btn btn-dark" type="submit"><i class="bi bi-search"></i> Buscar</button>' +
        '<button class="btn btn-outline-secondary" type="button" id="btnLimpar">Limpar</button></div>';

    const typeSel = document.getElementById('searchType');
    const activeSearch = () => searches[typeSel ? Number(typeSel.value) : 0];
    drawInputs(activeSearch());
    if (typeSel) typeSel.addEventListener('change', () => drawInputs(activeSearch()));

    form.addEventListener('submit', (ev) => {
      ev.preventDefault();
      const s = activeSearch();
      const query = s.params.map((p) =>
        encodeURIComponent(p.name) + '=' + encodeURIComponent(form.querySelector('[data-param="' + p.name + '"]').value.trim())
      ).join('&');
      current = { path: s.path, query };
      load();
    });
    document.getElementById('btnLimpar').addEventListener('click', () => {
      current = null;
      form.reset();
      drawInputs(activeSearch());
      load();
    });
  }

  document.getElementById('btnNovo').addEventListener('click', () => openForm(cfg, null, load));

  tbody.addEventListener('click', async (ev) => {
    const edit = ev.target.closest('[data-edit]');
    const del = ev.target.closest('[data-del]');
    if (edit) {
      // busca o registro completo na API para editar com dados atuais
      try {
        const full = await api('GET', cfg.endpoint + '/' + edit.dataset.edit);
        openForm(cfg, full, load);
      } catch (e) {
        toast(e.message, 'danger');
      }
    } else if (del) {
      const item = items.find((i) => String(i.id) === del.dataset.del);
      const ok = await confirmDialog('Excluir ' + cfg.singular.toLowerCase() + ' "' + cfg.label(item) + '"? Essa ação não pode ser desfeita.');
      if (!ok) return;
      try {
        await api('DELETE', cfg.endpoint + '/' + del.dataset.del);
        toast(cfg.singular + ' excluído(a).');
        load();
      } catch (e) {
        toast(e.message + (e.violations && e.violations.length ? ' ' + e.violations[0].message : ''), 'danger');
      }
    }
  });

  load();
}
