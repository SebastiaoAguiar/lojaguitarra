-- Estados (id_regiao: 1 Centro-Oeste, 2 Nordeste, 3 Norte, 4 Sudeste, 5 Sul)
insert into estado (nome, sigla, id_regiao) values('Tocantins', 'TO', 3);
insert into estado (nome, sigla, id_regiao) values('Goias', 'GO', 1);
insert into estado (nome, sigla, id_regiao) values('Rio de Janeiro', 'RJ', 4);
insert into estado (nome, sigla, id_regiao) values('Sao Paulo', 'SP', 4);
insert into estado (nome, sigla, id_regiao) values('Bahia', 'BA', 2);
insert into estado (nome, sigla, id_regiao) values('Parana', 'PR', 5);

-- Municipios
insert into municipio (nome, id_estado) values('Palmas', 1);
insert into municipio (nome, id_estado) values('Araguaina', 1);
insert into municipio (nome, id_estado) values('Goiania', 2);
insert into municipio (nome, id_estado) values('Rio de Janeiro', 3);
insert into municipio (nome, id_estado) values('Sao Paulo', 4);
insert into municipio (nome, id_estado) values('Campinas', 4);
insert into municipio (nome, id_estado) values('Salvador', 5);
insert into municipio (nome, id_estado) values('Curitiba', 6);

-- Marcas (ids gerados na ordem de insercao)
insert into marca (nome, pais_origem) values('Fender', 'Estados Unidos');   -- 1
insert into marca (nome, pais_origem) values('Gibson', 'Estados Unidos');   -- 2
insert into marca (nome, pais_origem) values('Epiphone', 'Estados Unidos'); -- 3
insert into marca (nome, pais_origem) values('Ibanez', 'Japao');            -- 4
insert into marca (nome, pais_origem) values('PRS', 'Estados Unidos');      -- 5
insert into marca (nome, pais_origem) values('Martin', 'Estados Unidos');   -- 6
insert into marca (nome, pais_origem) values('Yamaha', 'Japao');            -- 7
insert into marca (nome, pais_origem) values('Taylor', 'Estados Unidos');   -- 8
insert into marca (nome, pais_origem) values('Takamine', 'Japao');          -- 9

-- Captadores (usados pelas guitarras eletricas, N:N)
insert into captador (nome, tipo) values('Fender Vintage Noiseless', 'Single Coil'); -- 1
insert into captador (nome, tipo) values('Fender Texas Special', 'Single Coil');     -- 2
insert into captador (nome, tipo) values('Seymour Duncan JB', 'Humbucker');          -- 3
insert into captador (nome, tipo) values('Gibson 490R', 'Humbucker');                -- 4
insert into captador (nome, tipo) values('Gibson 498T', 'Humbucker');                -- 5
insert into captador (nome, tipo) values('Gibson P-90', 'P-90');                     -- 6

-- Especificacoes (composicao 1:1: uma por guitarra, na mesma ordem das guitarras)
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Stratocaster', 'Amieiro', 'Maple', 21);       -- 1
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Les Paul', 'Mogno', 'Ebano', 22);             -- 2
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('SG', 'Mogno', 'Louro', 22);                   -- 3
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Telecaster', 'Freixo', 'Maple', 21);          -- 4
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Superstrat', 'Tilia', 'Jatoba', 24);          -- 5
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Explorer', 'Mogno', 'Ebano', 22);             -- 6
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Jazzmaster', 'Amieiro', 'Pau-ferro', 22);     -- 7
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Double Cut', 'Mogno', 'Pau-ferro', 24);       -- 8
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Dreadnought', 'Abeto', 'Pau-ferro', 20);     -- 9
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Classico', 'Cedro', 'Pau-ferro', 19);        -- 10
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Grand Auditorium', 'Abeto', 'Ebano', 20);    -- 11
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Dreadnought', 'Abeto', 'Ebano', 20);         -- 12
insert into especificacao (formato_corpo, madeira_corpo, madeira_escala, numero_trastes) values('Thinline', 'Meranti', 'Pau-ferro', 20);      -- 13

-- Heranca JOINED: cada guitarra tem uma linha em "guitarra" (dados comuns)
-- e uma linha na tabela da subclasse com o MESMO id (dados especificos)

-- guitarras eletricas (ids 1 a 8)
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Stratocaster', 1, 'Player', 'Sunburst', 6500.00, 1);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Les Paul', 2, 'Standard 60s', 'Preta', 18500.00, 2);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('SG', 3, 'Standard', 'Vermelha', 4200.00, 3);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Telecaster', 1, 'Vintera', 'Branca', 7200.00, 4);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('RG550', 4, 'Genesis', 'Roadflare Red', 8900.00, 5);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Explorer', 2, 'Standard', 'Preta', 15200.00, 6);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('Jazzmaster', 1, 'Player', 'Azul', 7800.00, 7);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('PRS SE', 5, 'Custom 24', 'Vinho', 9500.00, 8);

insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(1, 'SSS', false, 'TREMOLO');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(2, 'HH', false, 'FIXA');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(3, 'HH', false, 'FIXA');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(4, 'SS', false, 'FIXA');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(5, 'HSH', false, 'FLOYD_ROSE');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(6, 'HH', false, 'FIXA');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(7, 'SS', false, 'TREMOLO');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(8, 'HH', false, 'TREMOLO');

-- Guitarra eletrica x Captador (N:N)
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

-- guitarras acusticas (ids 9 e 10)
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('D-28', 6, 'Standard', 'Natural', 22000.00, 9);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('C40', 7, 'Classica', 'Natural', 900.00, 10);

insert into guitarra_acustica (id, tipo_tampo, tipo_corda, cutaway) values(9, 'MACICO', 'ACO', false);
insert into guitarra_acustica (id, tipo_tampo, tipo_corda, cutaway) values(10, 'LAMINADO', 'NYLON', false);

-- guitarras eletroacusticas (ids 11 a 13)
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('214ce', 8, 'Grand Auditorium', 'Natural', 12500.00, 11);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('GD30CE', 9, 'Dreadnought', 'Preta', 3200.00, 12);
insert into guitarra (nome, id_marca, modelo, cor, preco, id_especificacao) values('APX600', 7, 'Thinline', 'Sunburst', 2100.00, 13);

insert into guitarra_eletroacustica (id, tipo_tampo, cutaway, tipo_captacao, afinador_embutido, bandas_equalizador) values(11, 'MACICO', true, 'PIEZO', false, 2);
insert into guitarra_eletroacustica (id, tipo_tampo, cutaway, tipo_captacao, afinador_embutido, bandas_equalizador) values(12, 'MACICO', true, 'PIEZO', true, 3);
insert into guitarra_eletroacustica (id, tipo_tampo, cutaway, tipo_captacao, afinador_embutido, bandas_equalizador) values(13, 'LAMINADO', true, 'PIEZO', true, 3);

-- Pessoas (heranca JOINED: pessoa + tabela filha com o mesmo id)
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
