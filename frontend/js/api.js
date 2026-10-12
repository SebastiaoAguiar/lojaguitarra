// endereco do backend Quarkus
const API_URL = 'http://localhost:8080';

class ApiError extends Error {
  constructor(status, message, violations) {
    super(message);
    this.status = status;
    this.violations = violations || [];
  }
}

// chamada generica a API; devolve o JSON (ou null em 204) e lanca ApiError nos erros
async function api(method, path, body) {
  let res;
  try {
    res = await fetch(API_URL + path, {
      method,
      headers: body ? { 'Content-Type': 'application/json' } : {},
      body: body ? JSON.stringify(body) : undefined,
    });
  } catch (e) {
    throw new ApiError(0, 'Nao foi possivel conectar a API em ' + API_URL + '. O backend esta rodando?');
  }

  if (!res.ok) {
    let problem = null;
    try { problem = await res.json(); } catch (e) { /* corpo vazio */ }
    throw new ApiError(
      res.status,
      (problem && problem.detail) || ('Erro ' + res.status),
      problem && problem.violations
    );
  }

  if (res.status === 204) return null;
  return res.json();
}
