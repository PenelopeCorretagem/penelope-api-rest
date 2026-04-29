ALTER TABLE empreendimento
    DROP FOREIGN KEY fk_empreendimento_endereco_stand;

ALTER TABLE empreendimento
    DROP COLUMN fk_endereco_stand;
