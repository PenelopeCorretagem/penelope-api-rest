-- Remove unique antigo de nome_completo em bancos legados.
SET @idx_nome_completo := (
    SELECT INDEX_NAME
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'usuario'
      AND COLUMN_NAME = 'nome_completo'
      AND NON_UNIQUE = 0
    LIMIT 1
);

SET @drop_sql := IF(
    @idx_nome_completo IS NOT NULL,
    CONCAT('ALTER TABLE usuario DROP INDEX `', @idx_nome_completo, '`'),
    'SELECT 1'
);

PREPARE stmt FROM @drop_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
