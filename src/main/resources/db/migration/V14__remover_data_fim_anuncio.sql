-- Remove a regra descontinuada de periodo de exibicao do anuncio.
SET @column_exists = (
    SELECT COUNT(*)
    FROM INFORMATION_SCHEMA.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'anuncio'
      AND COLUMN_NAME = 'data_fim'
);

SET @drop_data_fim = IF(
    @column_exists > 0,
    'ALTER TABLE anuncio DROP COLUMN data_fim',
    'SELECT 1'
);

PREPARE stmt FROM @drop_data_fim;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
