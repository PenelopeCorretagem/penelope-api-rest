-- Migração: remoção do tipo_evento do monolito
-- O gerenciamento de EventType foi delegado ao cal-service via RabbitMQ (estate.exchange → estate.changed).

-- 1. Remove a FK constraint antes de dropar a coluna
ALTER TABLE anuncio
    DROP FOREIGN KEY fk_anuncio_tipo_evento;

-- 2. Remove a coluna da tabela anuncio
ALTER TABLE anuncio
    DROP COLUMN fk_tipo_evento;

-- 3. Remove a tabela tipo_evento (não é mais responsabilidade deste serviço)
DROP TABLE tipo_evento;
