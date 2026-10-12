# Frontend - Loja Guitarra

Painel administrativo em HTML, CSS e JavaScript puro (Bootstrap 5 via CDN). Não precisa de build nem de `npm`.

## Como rodar

1. Suba o backend (na raiz do projeto): `./mvnw quarkus:dev` (API em http://localhost:8080).
2. Abra `frontend/index.html` no navegador. Se preferir um servidor local, use a extensão **Live Server** do VS Code (botão direito em `index.html` → *Open with Live Server*).

O endereço da API está em `js/api.js` (`API_URL`). O backend já libera CORS.

## Estrutura

- `index.html`: layout, menu e modais
- `css/style.css`: tema
- `js/api.js`: chamadas `fetch` à API e tratamento de erros (ProblemDetail)
- `js/ui.js`: utilitários (toast, confirmação, formatação)
- `js/crud.js`: motor de CRUD genérico (listagem, busca, formulário, exclusão)
- `js/entities.js`: configuração de cada tela (campos, colunas, buscas)
- `js/app.js`: rotas (`#/guitarras`, `#/clientes`, ...) e página inicial
