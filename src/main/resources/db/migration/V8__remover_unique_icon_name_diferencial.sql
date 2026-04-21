-- Remove unique constraint de icon_name na tabela diferencial
-- Permite que múltiplos diferenciais tenham o mesmo ícone
SET @idx_icon_name := (
    SELECT INDEX_NAME
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'diferencial'
      AND COLUMN_NAME = 'icon_name'
      AND NON_UNIQUE = 0
    LIMIT 1
);

SET @drop_sql := IF(
    @idx_icon_name IS NOT NULL,
    CONCAT('ALTER TABLE diferencial DROP INDEX `', @idx_icon_name, '`'),
    'SELECT 1'
);

PREPARE stmt FROM @drop_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
