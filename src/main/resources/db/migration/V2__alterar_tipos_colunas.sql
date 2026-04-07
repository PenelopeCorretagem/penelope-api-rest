-- Atualiza a coluna ativo e data_criacao na tabela de usuário
ALTER TABLE usuario MODIFY COLUMN ativo BOOLEAN DEFAULT TRUE;
ALTER TABLE usuario MODIFY COLUMN data_criacao DATE DEFAULT (CURRENT_DATE);

-- Atualiza as colunas ativo e destaque na tabela de anúncio
ALTER TABLE anuncio MODIFY COLUMN ativo BOOLEAN DEFAULT TRUE;
ALTER TABLE anuncio MODIFY COLUMN destaque BOOLEAN DEFAULT FALSE;