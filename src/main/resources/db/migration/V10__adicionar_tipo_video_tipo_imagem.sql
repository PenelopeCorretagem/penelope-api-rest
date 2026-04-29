INSERT INTO tipo_imagem (id, descricao)
SELECT 1, 'CAPA'
WHERE NOT EXISTS (
    SELECT 1 FROM tipo_imagem WHERE id = 1 OR descricao = 'CAPA'
);

INSERT INTO tipo_imagem (id, descricao)
SELECT 2, 'GALERIA'
WHERE NOT EXISTS (
    SELECT 1 FROM tipo_imagem WHERE id = 2 OR descricao = 'GALERIA'
);

INSERT INTO tipo_imagem (id, descricao)
SELECT 3, 'PLANTA'
WHERE NOT EXISTS (
    SELECT 1 FROM tipo_imagem WHERE id = 3 OR descricao = 'PLANTA'
);

INSERT INTO tipo_imagem (id, descricao)
SELECT 4, 'VIDEO'
WHERE NOT EXISTS (
    SELECT 1 FROM tipo_imagem WHERE id = 4 OR descricao = 'VIDEO'
);
