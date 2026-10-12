// Roteador simples por hash (#/guitarras, #/clientes, ...) e pagina inicial

const HOME_ORDER = ['guitarras', 'marcas', 'captadores', 'clientes', 'luthiers', 'estados', 'municipios'];

async function renderHome() {
  const app = document.getElementById('app');
  app.innerHTML =
    '<div class="hero mb-4">' +
      '<h1 class="fw-bold mb-1"><i class="bi bi-music-note-beamed accent"></i> Loja Guitarra</h1>' +
      '<p class="mb-0">Painel administrativo: cadastros de catálogo, pessoas e localização.</p>' +
    '</div>' +
    '<div id="apiAlert"></div>' +
    '<div class="row g-3 mb-4">' + HOME_ORDER.map((key) => {
      const e = ENTITIES[key];
      return '<div class="col-sm-6 col-lg-4"><a class="card entity-card shadow-sm h-100" href="#/' + key + '">' +
        '<div class="card-body d-flex align-items-center gap-3">' +
          '<div class="icon"><i class="bi ' + e.icon + '"></i></div>' +
          '<div class="flex-grow-1"><div class="fw-bold">' + esc(e.title) + '</div>' +
          '<div class="small text-muted">' + esc(e.subtitle) + '</div></div>' +
          '<div class="count" data-count="' + key + '">–</div>' +
        '</div></a></div>';
    }).join('') + '</div>' +
    '<div class="card shadow-sm"><div class="card-body">' +
      '<h5 class="mb-3"><i class="bi bi-diagram-3 accent me-2"></i>Relacionamentos do domínio</h5>' +
      '<div class="table-responsive"><table class="table table-sm align-middle mb-0">' +
      '<thead><tr><th>Tipo</th><th>Entidades</th><th>Cardinalidade</th></tr></thead><tbody>' +
        '<tr><td><span class="badge text-bg-dark">Herança</span></td><td>Pessoa → Cliente, Luthier</td><td>—</td></tr>' +
        '<tr><td><span class="badge text-bg-dark">Herança</span></td><td>Guitarra → Elétrica, Acústica, Eletroacústica</td><td>—</td></tr>' +
        '<tr><td><span class="badge text-bg-dark">Composição</span></td><td>Cliente → Endereço</td><td>1:N</td></tr>' +
        '<tr><td><span class="badge text-bg-dark">Composição</span></td><td>Guitarra → Especificação</td><td>1:1</td></tr>' +
        '<tr><td><span class="badge text-bg-dark">Agregação</span></td><td>Marca → Guitarra</td><td>1:N</td></tr>' +
        '<tr><td><span class="badge text-bg-dark">Associação</span></td><td>Guitarra Elétrica ↔ Captador</td><td>N:N</td></tr>' +
        '<tr><td><span class="badge text-bg-dark">Associação</span></td><td>Endereço → Município → Estado</td><td>N:1</td></tr>' +
      '</tbody></table></div>' +
    '</div></div>';

  // contagem de registros de cada CRUD
  const results = await Promise.allSettled(HOME_ORDER.map((key) => api('GET', ENTITIES[key].endpoint)));
  let offline = false;
  results.forEach((r, i) => {
    const el = document.querySelector('[data-count="' + HOME_ORDER[i] + '"]');
    if (!el) return;
    if (r.status === 'fulfilled') el.textContent = r.value.length;
    else offline = true;
  });
  if (offline) {
    document.getElementById('apiAlert').innerHTML =
      '<div class="alert alert-warning"><i class="bi bi-plug me-2"></i>Não foi possível buscar os dados na API (' +
      esc(API_URL) + '). Verifique se o backend está rodando.</div>';
  }
}

function route() {
  const key = location.hash.replace(/^#\/?/, '');
  document.querySelectorAll('[data-route]').forEach((a) => a.classList.toggle('active', a.dataset.route === key));
  if (ENTITIES[key]) renderCrud(ENTITIES[key]);
  else renderHome();
}

window.addEventListener('hashchange', route);
route();
