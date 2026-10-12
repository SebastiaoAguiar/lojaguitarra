-- Estados (idregiao: 1 Centro-Oeste, 2 Nordeste, 3 Norte, 4 Sudeste, 5 Sul)
insert into estado (nome, sigla, idregiao) values('Tocantins', 'TO', 3);
insert into estado (nome, sigla, idregiao) values('Goias', 'GO', 1);
insert into estado (nome, sigla, idregiao) values('Rio de Janeiro', 'RJ', 4);
insert into estado (nome, sigla, idregiao) values('Sao Paulo', 'SP', 4);
insert into estado (nome, sigla, idregiao) values('Bahia', 'BA', 2);
insert into estado (nome, sigla, idregiao) values('Parana', 'PR', 5);

-- Municipios
insert into municipio (nome, id_estado) values('Palmas', 1);
insert into municipio (nome, id_estado) values('Araguaina', 1);
insert into municipio (nome, id_estado) values('Goiania', 2);
insert into municipio (nome, id_estado) values('Rio de Janeiro', 3);
insert into municipio (nome, id_estado) values('Sao Paulo', 4);
insert into municipio (nome, id_estado) values('Campinas', 4);
insert into municipio (nome, id_estado) values('Salvador', 5);
insert into municipio (nome, id_estado) values('Curitiba', 6);

-- Marcas
insert into marca (nome, pais_origem) values('Fender', 'Estados Unidos');
insert into marca (nome, pais_origem) values('Gibson', 'Estados Unidos');
insert into marca (nome, pais_origem) values('Epiphone', 'Estados Unidos');
insert into marca (nome, pais_origem) values('Ibanez', 'Japao');
insert into marca (nome, pais_origem) values('PRS', 'Estados Unidos');

-- Captadores
insert into captador (nome, tipo) values('Fender Vintage Noiseless', 'Single Coil');
insert into captador (nome, tipo) values('Fender Texas Special', 'Single Coil');
insert into captador (nome, tipo) values('Seymour Duncan JB', 'Humbucker');
insert into captador (nome, tipo) values('Gibson 490R', 'Humbucker');
insert into captador (nome, tipo) values('Gibson 498T', 'Humbucker');
insert into captador (nome, tipo) values('Gibson P-90', 'P-90');

-- Especificacoes (uma por guitarra, na mesma ordem)
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Stratocaster', 'Amieiro', 'Maple', 21);
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Les Paul', 'Mogno', 'Ebano', 22);
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('SG', 'Mogno', 'Louro', 22);
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Telecaster', 'Freixo', 'Maple', 21);
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Superstrat', 'Tilia', 'Jatoba', 24);
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Explorer', 'Mogno', 'Ebano', 22);
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Jazzmaster', 'Amieiro', 'Pau-ferro', 22);
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Double Cut', 'Mogno', 'Pau-ferro', 24);

-- Guitarras
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Stratocaster', 1, 'Player', 'Sunburst', 6500.00, 1);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Les Paul', 2, 'Standard 60s', 'Preta', 18500.00, 2);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('SG', 3, 'Standard', 'Vermelha', 4200.00, 3);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Telecaster', 1, 'Vintera', 'Branca', 7200.00, 4);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('RG550', 4, 'Genesis', 'Roadflare Red', 8900.00, 5);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Explorer', 2, 'Standard', 'Preta', 15200.00, 6);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Jazzmaster', 1, 'Player', 'Azul', 7800.00, 7);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('PRS SE', 5, 'Custom 24', 'Vinho', 9500.00, 8);

-- Guitarra x Captador (N:N)
insert into guitarra_captador (id_guitarra, id_captador) values(1, 1);
insert into guitarra_captador (id_guitarra, id_captador) values(2, 4);
insert into guitarra_captador (id_guitarra, id_captador) values(2, 5);
insert into guitarra_captador (id_guitarra, id_captador) values(3, 4);
insert into guitarra_captador (id_guitarra, id_captador) values(4, 2);
insert into guitarra_captador (id_guitarra, id_captador) values(5, 3);
insert into guitarra_captador (id_guitarra, id_captador) values(6, 5);
insert into guitarra_captador (id_guitarra, id_captador) values(7, 6);
insert into guitarra_captador (id_guitarra, id_captador) values(8, 3);
insert into guitarra_captador (id_guitarra, id_captador) values(8, 4);

-- Pessoas (herenca JOINED: pessoa + tabela filha com o mesmo id)
insert into pessoa (nome, cpf, email) values('Ana Clara Souza', '12345678901', 'ana.clara@email.com');
insert into pessoa (nome, cpf, email) values('Bruno Martins Lima', '12345678902', 'bruno.martins@email.com');
insert into pessoa (nome, cpf, email) values('Carla Fernanda Alves', '12345678903', 'carla.alves@email.com');
insert into pessoa (nome, cpf, email) values('Diego Pereira Rocha', '98765432101', 'diego.rocha@email.com');
insert into pessoa (nome, cpf, email) values('Elisa Gomes Santos', '98765432102', 'elisa.santos@email.com');

-- Clientes (ids 1 a 3)
insert into cliente (id, telefone) values(1, '63991234567');
insert into cliente (id, telefone) values(2, '63992345678');
insert into cliente (id, telefone) values(3, '62993456789');

-- Luthiers (ids 4 e 5)
insert into luthier (id, especialidade, anos_experiencia) values(4, 'Regulagem e setup', 12);
insert into luthier (id, especialidade, anos_experiencia) values(5, 'Troca de captadores', 8);

-- Enderecos (composicao: cada um pertence a um cliente)
insert into endereco (logradouro, numero, complemento, bairro, cep, id_municipio, id_cliente) values('Rua das Palmeiras', '120', null, 'Centro', '77001000', 1, 1);
insert into endereco (logradouro, numero, complemento, bairro, cep, id_municipio, id_cliente) values('Avenida JK', '1500', 'Sala 3', 'Plano Diretor Sul', '77015000', 1, 1);
insert into endereco (logradouro, numero, complemento, bairro, cep, id_municipio, id_cliente) values('Avenida Tocantins', '450', null, 'Setor Central', '77800000', 2, 2);
insert into endereco (logradouro, numero, complemento, bairro, cep, id_municipio, id_cliente) values('Rua 10', '88', 'Apto 12', 'Setor Oeste', '74110000', 3, 3);
