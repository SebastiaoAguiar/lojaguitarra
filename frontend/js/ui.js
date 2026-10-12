// escapa texto antes de injetar em HTML
function esc(value) {
  return String(value ?? '')
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;');
}

const brl = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' });
function formatBRL(value) {
  return brl.format(Number(value || 0));
}

function formatCpf(cpf) {
  const d = String(cpf || '').replace(/\D/g, '');
  return d.length === 11 ? d.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4') : esc(cpf);
}

function toast(message, type) {
  const el = document.createElement('div');
  el.className = 'toast align-items-center text-bg-' + (type || 'success') + ' border-0';
  el.setAttribute('role', 'alert');
  el.innerHTML = '<div class="d-flex"><div class="toast-body">' + esc(message) +
    '</div><button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button></div>';
  document.getElementById('toasts').appendChild(el);
  const t = new bootstrap.Toast(el, { delay: 4000 });
  el.addEventListener('hidden.bs.toast', () => el.remove());
  t.show();
}

// abre o modal de confirmacao e devolve uma Promise<boolean>
function confirmDialog(message) {
  return new Promise((resolve) => {
    const modalEl = document.getElementById('confirmModal');
    const modal = bootstrap.Modal.getOrCreateInstance(modalEl);
    const okBtn = document.getElementById('confirmOk');
    document.getElementById('confirmText').textContent = message;

    let answered = false;
    const finish = (value) => {
      if (answered) return;
      answered = true;
      okBtn.removeEventListener('click', onOk);
      modalEl.removeEventListener('hidden.bs.modal', onHidden);
      resolve(value);
    };
    const onOk = () => { finish(true); modal.hide(); };
    const onHidden = () => finish(false);

    okBtn.addEventListener('click', onOk);
    modalEl.addEventListener('hidden.bs.modal', onHidden);
    modal.show();
  });
}
