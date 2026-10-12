// Configuracao de cada CRUD. Os campos seguem o formato dos DTOs do backend.

const REGIOES = [
  { value: 1, label: 'Centro-Oeste' },
  { value: 2, label: 'Nordeste' },
  { value: 3, label: 'Norte' },
  { value: 4, label: 'Sudeste' },
  { value: 5, label: 'Sul' },
];

const nomeSearch = (path) => ({
  key: 'nome', label: 'Nome', path,
  params: [{ name: 'nome', label: 'Nome', placeholder: 'Digite parte do nome' }],
});

const optionsFrom = (path, label) => async () =>
  (await api('GET', path)).map((o) => ({ value: o.id, label: label(o) }));

const ENTITIES = {
  estados: {
    title: 'Estados', singular: 'Estado', icon: 'bi-map', endpoint: '/estados',
    subtitle: 'Unidades federativas e suas regiões.',
    label: (i) => i.nome,
    columns: [
      { label: 'Nome', render: (i) => '<strong>' + esc(i.nome) + '</strong>' },
      { label: 'Sigla', render: (i) => '<span class="badge badge-soft">' + esc(i.sigla) + '</span>' },
      { label: 'Região', render: (i) => esc(i.regiao && i.regiao.nome) },
    ],
    searches: [nomeSearch('/estados/search')],
    fields: [
      { name: 'nome', label: 'Nome', type: 'text', col: 6 },
      { name: 'sigla', label: 'Sigla', type: 'text', col: 3, maxlength: 2, pattern: '[A-Z]{2}', placeholder: 'TO' },
      { name: 'idRegiao', label: 'Região', type: 'select', col: 3, numeric: true, options: REGIOES },
    ],
    get: (i) => ({ nome: i.nome, sigla: i.sigla, idRegiao: i.regiao && i.regiao.id }),
  },

  municipios: {
    title: 'Municípios', singular: 'Município', icon: 'bi-geo-alt', endpoint: '/municipios',
    subtitle: 'Cada município pertence a um estado (N:1).',
    label: (i) => i.nome,
    columns: [
      { label: 'Nome', render: (i) => '<strong>' + esc(i.nome) + '</strong>' },
      { label: 'Estado', render: (i) => esc(i.estado.nome) + ' <span class="badge badge-soft">' + esc(i.estado.sigla) + '</span>' },
    ],
    searches: [nomeSearch('/municipios/search')],
    fields: [
      { name: 'nome', label: 'Nome', type: 'text', col: 7 },
      { name: 'idEstado', label: 'Estado', type: 'select', col: 5, numeric: true,
        options: optionsFrom('/estados', (e) => e.nome + ' (' + e.sigla + ')') },
    ],
    get: (i) => ({ nome: i.nome, idEstado: i.estado.id }),
  },

  marcas: {
    title: 'Marcas', singular: 'Marca', icon: 'bi-award', endpoint: '/marcas',
    subtitle: 'Fabricantes. A marca existe independente das guitarras (agregação).',
    label: (i) => i.nome,
    columns: [
      { label: 'Nome', render: (i) => '<strong>' + esc(i.nome) + '</strong>' },
      { label: 'País de origem', render: (i) => esc(i.paisOrigem) },
    ],
    searches: [nomeSearch('/marcas/search')],
    fields: [
      { name: 'nome', label: 'Nome', type: 'text', col: 6 },
      { name: 'paisOrigem', label: 'País de origem', type: 'text', col: 6 },
    ],
    get: (i) => ({ nome: i.nome, paisOrigem: i.paisOrigem }),
  },

  captadores: {
    title: 'Captadores', singular: 'Captador', icon: 'bi-soundwave', endpoint: '/captadores',
    subtitle: 'Um captador pode equipar várias guitarras (N:N).',
    label: (i) => i.nome,
    columns: [
      { label: 'Nome', render: (i) => '<strong>' + esc(i.nome) + '</strong>' },
      { label: 'Tipo', render: (i) => '<span class="badge badge-soft">' + esc(i.tipo) + '</span>' },
    ],
    searches: [nomeSearch('/captadores/search')],
    fields: [
      { name: 'nome', label: 'Nome', type: 'text', col: 7 },
      { name: 'tipo', label: 'Tipo', type: 'text', col: 5, suggestions: ['Single Coil', 'Humbucker', 'P-90'] },
    ],
    get: (i) => ({ nome: i.nome, tipo: i.tipo }),
  },

  guitarras: {
    title: 'Guitarras', singular: 'Guitarra', icon: 'bi-vinyl', endpoint: '/guitarras',
    subtitle: 'Catálogo com marca, especificação (1:1, composição) e captadores (N:N).',
    label: (i) => i.nome,
    columns: [
      { label: 'Nome', render: (i) => '<strong>' + esc(i.nome) + '</strong><div class="small text-muted">' + esc(i.modelo) + '</div>' },
      { label: 'Marca', render: (i) => esc(i.marca.nome) },
      { label: 'Cor', render: (i) => esc(i.cor) },
      { label: 'Preço', render: (i) => formatBRL(i.preco) },
      { label: 'Corpo', render: (i) => esc(i.especificacao.formatoCorpo) + '<div class="small text-muted">' + esc(i.especificacao.numeroTrastes) + ' trastes</div>' },
      { label: 'Captadores', render: (i) => i.captadores.map((c) => '<span class="badge badge-soft me-1">' + esc(c.nome) + '</span>').join('') },
    ],
    searches: [
      nomeSearch('/guitarras/search'),
      { key: 'marca', label: 'Marca', path: '/guitarras/search/marca', params: [{ name: 'nome', label: 'Marca', placeholder: 'Ex.: Fender' }] },
      { key: 'modelo', label: 'Modelo', path: '/guitarras/search/modelo', params: [{ name: 'modelo', label: 'Modelo' }] },
      { key: 'cor', label: 'Cor', path: '/guitarras/search/cor', params: [{ name: 'cor', label: 'Cor' }] },
      { key: 'preco', label: 'Faixa de preço', path: '/guitarras/search/preco',
        params: [{ name: 'min', label: 'Mínimo (R$)', type: 'number' }, { name: 'max', label: 'Máximo (R$)', type: 'number' }] },
    ],
    fields: [
      { name: 'nome', label: 'Nome', type: 'text', col: 6 },
      { name: 'idMarca', label: 'Marca', type: 'select', col: 6, numeric: true, options: optionsFrom('/marcas', (m) => m.nome) },
      { name: 'modelo', label: 'Modelo', type: 'text', col: 4 },
      { name: 'cor', label: 'Cor', type: 'text', col: 4 },
      { name: 'preco', label: 'Preço (R$)', type: 'number', col: 4, step: 0.01, min: 0.01 },
      { name: 'especificacao', label: 'Especificação', type: 'group', fields: [
        { name: 'formatoCorpo', label: 'Formato do corpo', type: 'text', col: 6 },
        { name: 'madeiraCorpo', label: 'Madeira do corpo', type: 'text', col: 6 },
        { name: 'madeiraEscala', label: 'Madeira da escala', type: 'text', col: 6 },
        { name: 'numeroTrastes', label: 'Nº de trastes', type: 'number', col: 6, min: 18, max: 30 },
      ] },
      { name: 'idsCaptadores', label: 'Captadores', type: 'checks',
        options: optionsFrom('/captadores', (c) => c.nome + ' — ' + c.tipo) },
    ],
    get: (i) => ({
      nome: i.nome, idMarca: i.marca.id, modelo: i.modelo, cor: i.cor, preco: i.preco,
      especificacao: i.especificacao,
      idsCaptadores: i.captadores.map((c) => c.id),
    }),
  },

  clientes: {
    title: 'Clientes', singular: 'Cliente', icon: 'bi-person', endpoint: '/clientes',
    subtitle: 'Pessoa que compra. Os endereços fazem parte do cliente (composição 1:N).',
    label: (i) => i.nome,
    columns: [
      { label: 'Nome', render: (i) => '<strong>' + esc(i.nome) + '</strong><div class="small text-muted">' + esc(i.email) + '</div>' },
      { label: 'CPF', render: (i) => formatCpf(i.cpf) },
      { label: 'Telefone', render: (i) => esc(i.telefone) },
      { label: 'Endereços', render: (i) => i.enderecos.map((e) =>
          '<div class="small">' + esc(e.logradouro) + ', ' + esc(e.numero) + ' — ' + esc(e.municipio.nome) + '/' + esc(e.municipio.estado.sigla) + '</div>').join('') },
    ],
    searches: [nomeSearch('/clientes/search')],
    fields: [
      { name: 'nome', label: 'Nome', type: 'text', col: 6 },
      { name: 'cpf', label: 'CPF (só números)', type: 'text', col: 3, maxlength: 11, pattern: '\\d{11}' },
      { name: 'telefone', label: 'Telefone', type: 'text', col: 3 },
      { name: 'email', label: 'E-mail', type: 'email', col: 12 },
      { name: 'enderecos', label: 'Endereços', type: 'list', addLabel: 'Adicionar endereço', fields: [
        { name: 'logradouro', label: 'Logradouro', type: 'text', col: 6 },
        { name: 'numero', label: 'Número', type: 'text', col: 2 },
        { name: 'complemento', label: 'Complemento', type: 'text', col: 4, required: false },
        { name: 'bairro', label: 'Bairro', type: 'text', col: 4 },
        { name: 'cep', label: 'CEP (só números)', type: 'text', col: 3, maxlength: 8, pattern: '\\d{8}' },
        { name: 'idMunicipio', label: 'Município', type: 'select', col: 5, numeric: true,
          options: optionsFrom('/municipios', (m) => m.nome + ' - ' + m.estado.sigla) },
      ] },
    ],
    get: (i) => ({
      nome: i.nome, cpf: i.cpf, telefone: i.telefone, email: i.email,
      enderecos: i.enderecos.map((e) => ({
        logradouro: e.logradouro, numero: e.numero, complemento: e.complemento,
        bairro: e.bairro, cep: e.cep, idMunicipio: e.municipio.id,
      })),
    }),
  },

  luthiers: {
    title: 'Luthiers', singular: 'Luthier', icon: 'bi-tools', endpoint: '/luthiers',
    subtitle: 'Profissional que regula e conserta as guitarras.',
    label: (i) => i.nome,
    columns: [
      { label: 'Nome', render: (i) => '<strong>' + esc(i.nome) + '</strong><div class="small text-muted">' + esc(i.email) + '</div>' },
      { label: 'CPF', render: (i) => formatCpf(i.cpf) },
      { label: 'Especialidade', render: (i) => esc(i.especialidade) },
      { label: 'Experiência', render: (i) => esc(i.anosExperiencia) + ' anos' },
    ],
    searches: [
      nomeSearch('/luthiers/search'),
      { key: 'esp', label: 'Especialidade', path: '/luthiers/search/especialidade',
        params: [{ name: 'especialidade', label: 'Especialidade' }] },
    ],
    fields: [
      { name: 'nome', label: 'Nome', type: 'text', col: 6 },
      { name: 'cpf', label: 'CPF (só números)', type: 'text', col: 3, maxlength: 11, pattern: '\\d{11}' },
      { name: 'email', label: 'E-mail', type: 'email', col: 3 },
      { name: 'especialidade', label: 'Especialidade', type: 'text', col: 8 },
      { name: 'anosExperiencia', label: 'Anos de experiência', type: 'number', col: 4, min: 0, max: 60 },
    ],
    get: (i) => ({
      nome: i.nome, cpf: i.cpf, email: i.email,
      especialidade: i.especialidade, anosExperiencia: i.anosExperiencia,
    }),
  },
};
