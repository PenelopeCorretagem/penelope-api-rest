-- ========================================
-- 1. INSERIR USUÁRIOS (somente 3)
-- ========================================

INSERT INTO usuario (id, nome_completo, email, senha, cpf, data_nascimento, renda_mensal, telefone, creci, ativo, nivel_acesso)
VALUES (
           1,
           'Maria Santos Silva',
           'maria.santos@penelope.com.br',
           '$2a$10$XYZ123',
           '12345678901',
           '1985-03-15',
           8000.00,
           '11987654321',
           'CRECI-SP-123456',
           1,
           'Administrador'
       );

INSERT INTO usuario (id, nome_completo, email, senha, cpf, data_nascimento, renda_mensal, telefone, creci, ativo, nivel_acesso)
VALUES (
           2,
           'Pedro Oliveira Costa',
           'pedro.oliveira@penelope.com.br',
           '$2a$10$ABC456',
           '23456789012',
           '1990-07-20',
           7500.00,
           '11976543210',
           'CRECI-SP-234567',
           1,
           'Administrador'
       );

INSERT INTO usuario (id, nome_completo, email, senha, cpf, data_nascimento, renda_mensal, telefone, creci, ativo, nivel_acesso)
VALUES (
           3,
           'João Silva Santos',
           'joao.silva@email.com',
           '$2a$10$DEF789',
           '34567890123',
           '1988-11-10',
           15000.00,
           '11965432109',
           NULL,
           1,
           'Cliente'
       );



-- ========================================
-- 2. INSERIR TIPO_IMAGEM (apenas 4)
-- ========================================

INSERT INTO tipo_imagem (id, descricao) VALUES (1, 'Capa');
INSERT INTO tipo_imagem (id, descricao) VALUES (2, 'Galeria');
INSERT INTO tipo_imagem (id, descricao) VALUES (3, 'Planta');
INSERT INTO tipo_imagem (id, descricao) VALUES (4, 'Video');



-- ========================================
-- 3. INSERIR DIFERENCIAIS (lista solicitada)
-- ========================================

INSERT INTO diferencial (id, descricao) VALUES (1, 'QUADRA_BASQUETE');
INSERT INTO diferencial (id, descricao) VALUES (2, 'CESTA');
INSERT INTO diferencial (id, descricao) VALUES (3, 'CINEMA');
INSERT INTO diferencial (id, descricao) VALUES (4, 'OFICINA');
INSERT INTO diferencial (id, descricao) VALUES (5, 'PET');
INSERT INTO diferencial (id, descricao) VALUES (6, 'FLORESTA');
INSERT INTO diferencial (id, descricao) VALUES (7, 'BRINQUEDO');
INSERT INTO diferencial (id, descricao) VALUES (8, 'LOUNGE');
INSERT INTO diferencial (id, descricao) VALUES (9, 'YOGA');
INSERT INTO diferencial (id, descricao) VALUES (10, 'MEDITACAO');
INSERT INTO diferencial (id, descricao) VALUES (11, 'BRINDE');
INSERT INTO diferencial (id, descricao) VALUES (12, 'LAVANDERIA');
INSERT INTO diferencial (id, descricao) VALUES (13, 'SOLARIO');
INSERT INTO diferencial (id, descricao) VALUES (14, 'MERCADO');
INSERT INTO diferencial (id, descricao) VALUES (15, 'TENIS');
INSERT INTO diferencial (id, descricao) VALUES (16, 'CHURRASQUEIRA');
INSERT INTO diferencial (id, descricao) VALUES (17, 'HORTA');
INSERT INTO diferencial (id, descricao) VALUES (18, 'GAMES');
