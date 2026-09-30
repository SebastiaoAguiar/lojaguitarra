-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

-- marcas (ids gerados na ordem de insercao)
insert into marca (nome) values('Fender');   -- 1
insert into marca (nome) values('Gibson');   -- 2
insert into marca (nome) values('Epiphone'); -- 3
insert into marca (nome) values('Ibanez');   -- 4
insert into marca (nome) values('PRS');      -- 5
insert into marca (nome) values('Martin');   -- 6
insert into marca (nome) values('Yamaha');   -- 7
insert into marca (nome) values('Taylor');   -- 8
insert into marca (nome) values('Takamine'); -- 9

-- heranca JOINED: cada guitarra tem uma linha em "guitarra" (dados comuns)
-- e uma linha na tabela da subclasse com o MESMO id (dados especificos)

-- guitarras eletricas (ids 1 a 8)
insert into guitarra (nome, id_marca, modelo, cor, preco) values('Stratocaster', 1, 'Player', 'Sunburst', 6500.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('Les Paul', 2, 'Standard 60s', 'Preta', 18500.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('SG', 3, 'Standard', 'Vermelha', 4200.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('Telecaster', 1, 'Vintera', 'Branca', 7200.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('RG550', 4, 'Genesis', 'Roadflare Red', 8900.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('Explorer', 2, 'Standard', 'Preta', 15200.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('Jazzmaster', 1, 'Player', 'Azul', 7800.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('PRS SE', 5, 'Custom 24', 'Vinho', 9500.00);

insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(1, 'SSS', false, 'TREMOLO');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(2, 'HH', false, 'FIXA');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(3, 'HH', false, 'FIXA');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(4, 'SS', false, 'FIXA');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(5, 'HSH', false, 'FLOYD_ROSE');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(6, 'HH', false, 'FIXA');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(7, 'SS', false, 'TREMOLO');
insert into guitarra_eletrica (id, configuracao_captadores, captacao_ativa, tipo_ponte) values(8, 'HH', false, 'TREMOLO');

-- guitarras acusticas (ids 9 e 10)
insert into guitarra (nome, id_marca, modelo, cor, preco) values('D-28', 6, 'Standard', 'Natural', 22000.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('C40', 7, 'Classica', 'Natural', 900.00);

insert into guitarra_acustica (id, tipo_tampo, tipo_corda, cutaway) values(9, 'MACICO', 'ACO', false);
insert into guitarra_acustica (id, tipo_tampo, tipo_corda, cutaway) values(10, 'LAMINADO', 'NYLON', false);

-- guitarras eletroacusticas (ids 11 a 13)
insert into guitarra (nome, id_marca, modelo, cor, preco) values('214ce', 8, 'Grand Auditorium', 'Natural', 12500.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('GD30CE', 9, 'Dreadnought', 'Preta', 3200.00);
insert into guitarra (nome, id_marca, modelo, cor, preco) values('APX600', 7, 'Thinline', 'Sunburst', 2100.00);

insert into guitarra_eletroacustica (id, tipo_tampo, cutaway, tipo_captacao, afinador_embutido, bandas_equalizador) values(11, 'MACICO', true, 'PIEZO', false, 2);
insert into guitarra_eletroacustica (id, tipo_tampo, cutaway, tipo_captacao, afinador_embutido, bandas_equalizador) values(12, 'MACICO', true, 'PIEZO', true, 3);
insert into guitarra_eletroacustica (id, tipo_tampo, cutaway, tipo_captacao, afinador_embutido, bandas_equalizador) values(13, 'LAMINADO', true, 'PIEZO', true, 3);
