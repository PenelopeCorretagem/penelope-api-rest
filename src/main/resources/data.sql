-- ========================================
-- 1. INSERIR ENDEREÇOS
-- ========================================

-- Endereços dos empreendimentos
INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (1, 'Av. Paulista', 1000, 'Bela Vista', 'São Paulo', 'SP', 'Sudeste', '01310100', 'Torre A');

INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (2, 'Rua dos Pinheiros', 500, 'Pinheiros', 'São Paulo', 'SP', 'Sudeste', '05422001', NULL);

INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (3, 'Av. Atlântica', 2000, 'Copacabana', 'Rio de Janeiro', 'RJ', 'Sudeste', '22021001', 'Frente Mar');

INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (4, 'Rua das Flores', 150, 'Jardins', 'São Paulo', 'SP', 'Sudeste', '01424020', 'Bloco B');

INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (5, 'Av. Boa Viagem', 3000, 'Boa Viagem', 'Recife', 'PE', 'Nordeste', '51020001', 'Ed. Costa Azul');

-- Endereços dos stands de vendas
INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (6, 'Av. Paulista', 1010, 'Bela Vista', 'São Paulo', 'SP', 'Sudeste', '01310100', 'Loja 1');

INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (7, 'Rua dos Pinheiros', 510, 'Pinheiros', 'São Paulo', 'SP', 'Sudeste', '05422001', 'Stand Vendas');

INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (8, 'Av. Atlântica', 2010, 'Copacabana', 'Rio de Janeiro', 'RJ', 'Sudeste', '22021001', 'Sala Comercial');

INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (9, 'Rua das Flores', 160, 'Jardins', 'São Paulo', 'SP', 'Sudeste', '01424020', 'Térreo');

INSERT INTO endereco (id, rua, numero, bairro, cidade, uf, regiao, cep, complemento)
VALUES (10, 'Av. Boa Viagem', 3010, 'Boa Viagem', 'Recife', 'PE', 'Nordeste', '51020001', 'Loja 5');

-- ========================================
-- 2. INSERIR EMPREENDIMENTOS
-- ========================================

INSERT INTO empreendimento (id, titulo, descricao, area, quartos, tipo, fk_endereco, fk_endereco_stand)
VALUES (
           1,
           'Residencial Paulista Premium',
           'Apartamentos de alto padrão no coração da Av. Paulista com lazer completo',
           120.50,
           3,
           'DISPONIVEL',
           1,
           6
       );

INSERT INTO empreendimento (id, titulo, descricao, area, quartos, tipo, fk_endereco, fk_endereco_stand)
VALUES (
           2,
           'Pinheiros Urban Living',
           'Empreendimento moderno próximo ao metrô. Entrega prevista para 2026',
           95.00,
           2,
           'EM_OBRAS',
           2,
           7
       );

INSERT INTO empreendimento (id, titulo, descricao, area, quartos, tipo, fk_endereco, fk_endereco_stand)
VALUES (
           3,
           'Copacabana Beach Front',
           'Apartamentos com vista para o mar. Lançamento exclusivo de luxo',
           180.00,
           4,
           'LANCAMENTO',
           3,
           8
       );

INSERT INTO empreendimento (id, titulo, descricao, area, quartos, tipo, fk_endereco, fk_endereco_stand)
VALUES (
           4,
           'Jardins Residence',
           'Cobertura duplex com terraço. Pronto para morar',
           250.00,
           4,
           'DISPONIVEL',
           4,
           9
       );

INSERT INTO empreendimento (id, titulo, descricao, area, quartos, tipo, fk_endereco, fk_endereco_stand)
VALUES (
           5,
           'Boa Viagem Sunset',
           'Apartamentos à beira-mar com 3 suítes. Projeto sustentável',
           145.00,
           3,
           'LANCAMENTO',
           5,
           10
       );

-- ========================================
-- 3. INSERIR USUÁRIOS (Corretores e Clientes)
-- ========================================

-- Corretores
INSERT INTO usuario (id, nome_completo, email, senha, cpf, data_nascimento, renda_mensal, telefone, creci, ativo, nivel_acesso)
VALUES (
           1,
           'Maria Santos Silva',
           'maria.santos@penelope.com.br',
           '$2a$10$XYZ123', -- senha hasheada
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

-- Clientes
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

INSERT INTO usuario (id, nome_completo, email, senha, cpf, data_nascimento, renda_mensal, telefone, creci, ativo, nivel_acesso)
VALUES (
           4,
           'Ana Costa Mendes',
           'ana.costa@email.com',
           '$2a$10$GHI012',
           '45678901234',
           '1992-05-25',
           12000.00,
           '11954321098',
           NULL,
           1,
           'Cliente'
       );

INSERT INTO usuario (id, nome_completo, email, senha, cpf, data_nascimento, renda_mensal, telefone, creci, ativo, nivel_acesso)
VALUES (
           5,
           'Carlos Mendes Alves',
           'carlos.mendes@email.com',
           '$2a$10$JKL345',
           '56789012345',
           '1995-09-30',
           18000.00,
           '11943210987',
           NULL,
           1,
           'Cliente'
       );

-- ========================================
-- 4. INSERIR ANÚNCIOS
-- ========================================

INSERT INTO anuncio (id, fk_empreendimento, fk_criador, fk_responsavel, ativo, destaque, data_fim)
VALUES (1, 1, 1, 1, 1, 1, '2026-12-31');

INSERT INTO anuncio (id, fk_empreendimento, fk_criador, fk_responsavel, ativo, destaque, data_fim)
VALUES (2, 2, 1, 2, 1, 0, '2026-06-30');

INSERT INTO anuncio (id, fk_empreendimento, fk_criador, fk_responsavel, ativo, destaque, data_fim)
VALUES (3, 3, 2, 1, 1, 1, '2025-12-31');

INSERT INTO anuncio (id, fk_empreendimento, fk_criador, fk_responsavel, ativo, destaque, data_fim)
VALUES (4, 4, 1, 2, 1, 0, '2026-03-31');

INSERT INTO anuncio (id, fk_empreendimento, fk_criador, fk_responsavel, ativo, destaque, data_fim)
VALUES (5, 5, 2, 2, 1, 1, '2025-11-30');

-- ========================================
-- 5. INSERIR TIPOS DE IMAGEM
-- ========================================

INSERT INTO tipo_imagem (id, descricao) VALUES (1, 'Fachada');
INSERT INTO tipo_imagem (id, descricao) VALUES (2, 'Área de Lazer');
INSERT INTO tipo_imagem (id, descricao) VALUES (3, 'Interior');
INSERT INTO tipo_imagem (id, descricao) VALUES (4, 'Perspectiva Artística');
INSERT INTO tipo_imagem (id, descricao) VALUES (5, 'Planta Baixa');

-- ========================================
-- 6. INSERIR IMAGENS DOS EMPREENDIMENTOS
-- ========================================

INSERT INTO imagem_empreendimento (id, fk_empreendimento, fk_tipo_imagem, url)
VALUES (1, 1, 1, 'https://exemplo.com/paulista-premium-fachada.jpg');

INSERT INTO imagem_empreendimento (id, fk_empreendimento, fk_tipo_imagem, url)
VALUES (2, 1, 2, 'https://exemplo.com/paulista-premium-lazer.jpg');

INSERT INTO imagem_empreendimento (id, fk_empreendimento, fk_tipo_imagem, url)
VALUES (3, 2, 4, 'https://exemplo.com/pinheiros-perspectiva.jpg');

INSERT INTO imagem_empreendimento (id, fk_empreendimento, fk_tipo_imagem, url)
VALUES (4, 3, 1, 'https://exemplo.com/copacabana-fachada.jpg');

INSERT INTO imagem_empreendimento (id, fk_empreendimento, fk_tipo_imagem, url)
VALUES (5, 4, 3, 'https://exemplo.com/jardins-interior.jpg');

INSERT INTO imagem_empreendimento (id, fk_empreendimento, fk_tipo_imagem, url)
VALUES (6, 5, 1, 'https://exemplo.com/boa-viagem-fachada.jpg');

-- ========================================
-- 7. INSERIR DIFERENCIAIS
-- ========================================

INSERT INTO diferencial (id, descricao) VALUES (1, 'Piscina');
INSERT INTO diferencial (id, descricao) VALUES (2, 'Academia');
INSERT INTO diferencial (id, descricao) VALUES (3, 'Salão de Festas');
INSERT INTO diferencial (id, descricao) VALUES (4, 'Playground');
INSERT INTO diferencial (id, descricao) VALUES (5, 'Segurança 24h');
INSERT INTO diferencial (id, descricao) VALUES (6, 'Coworking');
INSERT INTO diferencial (id, descricao) VALUES (7, 'Bike Space');
INSERT INTO diferencial (id, descricao) VALUES (8, 'Vista Mar');
INSERT INTO diferencial (id, descricao) VALUES (9, 'Varanda Gourmet');
INSERT INTO diferencial (id, descricao) VALUES (10, 'Spa');
INSERT INTO diferencial (id, descricao) VALUES (11, 'Pet Place');
INSERT INTO diferencial (id, descricao) VALUES (12, 'Rooftop');
INSERT INTO diferencial (id, descricao) VALUES (13, 'Placas Solares');

-- ========================================
-- 8. ASSOCIAR DIFERENCIAIS AOS EMPREENDIMENTOS
-- ========================================

-- Empreendimento 1 (Paulista Premium)
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (1, 1); -- Piscina
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (1, 2); -- Academia
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (1, 3); -- Salão de Festas
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (1, 5); -- Segurança 24h

-- Empreendimento 2 (Pinheiros Urban)
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (2, 6); -- Coworking
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (2, 7); -- Bike Space

-- Empreendimento 3 (Copacabana Beach)
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (3, 8); -- Vista Mar
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (3, 9); -- Varanda Gourmet
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (3, 10); -- Spa

-- Empreendimento 4 (Jardins Residence)
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (4, 11); -- Pet Place
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (4, 4); -- Playground
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (4, 1); -- Piscina

-- Empreendimento 5 (Boa Viagem Sunset)
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (5, 13); -- Placas Solares
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (5, 12); -- Rooftop
INSERT INTO diferencial_empreendimento (fk_empreendimento, fk_diferencial) VALUES (5, 8); -- Vista Mar