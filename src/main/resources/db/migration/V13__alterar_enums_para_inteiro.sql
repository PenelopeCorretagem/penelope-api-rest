-- Converte nivel_acesso de texto para inteiro
ALTER TABLE usuario ADD COLUMN nivel_acesso_int INT;
UPDATE usuario
SET nivel_acesso_int = CASE nivel_acesso
    WHEN 'ADMINISTRADOR' THEN 1
    WHEN 'CLIENTE' THEN 2
    WHEN 'CORRETOR' THEN 3
    ELSE NULL
END;
ALTER TABLE usuario DROP COLUMN nivel_acesso;
ALTER TABLE usuario CHANGE COLUMN nivel_acesso_int nivel_acesso INT NOT NULL DEFAULT 2;

-- Converte tipo de ENUM para inteiro
ALTER TABLE empreendimento ADD COLUMN tipo_int INT;
UPDATE empreendimento
SET tipo_int = CASE tipo
    WHEN 'EM_OBRAS' THEN 1
    WHEN 'LANCAMENTO' THEN 2
    WHEN 'DISPONIVEL' THEN 3
    ELSE NULL
END;
ALTER TABLE empreendimento DROP COLUMN tipo;
ALTER TABLE empreendimento CHANGE COLUMN tipo_int tipo INT NOT NULL;
