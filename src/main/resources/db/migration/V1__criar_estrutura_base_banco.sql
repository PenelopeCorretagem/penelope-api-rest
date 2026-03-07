-- Tabela de endereços, associada à cidade
CREATE TABLE endereco (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(150) NOT NULL,
    numero VARCHAR(5) NOT NULL,
    bairro VARCHAR(100),
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    regiao VARCHAR(100) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    complemento VARCHAR(100)
);

-- Tabela de usuários do sistema
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_completo VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) UNIQUE,
    data_nascimento DATE NOT NULL,
    renda_mensal DECIMAL(10, 2),
    telefone VARCHAR(20),
    creci VARCHAR(20) UNIQUE,
    ativo TINYINT(1) DEFAULT 1,
    nivel_acesso ENUM('Administrador', 'Cliente') NOT NULL DEFAULT 'Cliente',
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    token_redefinicao_senha VARCHAR(255),
    data_expiracao_token DATETIME
);

-- Tabela de empreendimentos imobiliários
CREATE TABLE empreendimento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descricao VARCHAR(150) NOT NULL,
    area FLOAT NOT NULL,
    quartos INT NOT NULL,
    tipo ENUM(
        'Disponível',
        'Em obras',
        'Lançamento'
    ) NOT NULL,
    fk_endereco BIGINT NOT NULL,
    fk_endereco_stand BIGINT,
    CONSTRAINT fk_empreendimento_endereco FOREIGN KEY (fk_endereco) REFERENCES endereco (id),
    CONSTRAINT fk_empreendimento_endereco_stand FOREIGN KEY (fk_endereco_stand) REFERENCES endereco (id)
);

CREATE TABLE tipo_evento (
    id BIGINT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    slug VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela de anúncios de empreendimentos
CREATE TABLE anuncio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_empreendimento BIGINT NOT NULL,
    fk_criador BIGINT,
    fk_responsavel BIGINT,
    ativo TINYINT(1) DEFAULT 1,
    destaque TINYINT(1) DEFAULT 0,
    data_criacao DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_fim DATE,
    fk_tipo_evento BIGINT,
    CONSTRAINT fk_anuncio_empreendimento FOREIGN KEY (fk_empreendimento) REFERENCES empreendimento (id) ON DELETE CASCADE,
    CONSTRAINT fk_anuncio_criador FOREIGN KEY (fk_criador) REFERENCES usuario (id) ON DELETE SET NULL,
    CONSTRAINT fk_anuncio_responsavel FOREIGN KEY (fk_responsavel) REFERENCES usuario (id) ON DELETE SET NULL,
    CONSTRAINT fk_anuncio_tipo_evento FOREIGN KEY (fk_tipo_evento) REFERENCES tipo_evento (id) ON DELETE SET NULL
);

CREATE TABLE tipo_imagem (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE imagem_empreendimento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_empreendimento BIGINT NOT NULL,
    fk_tipo_imagem BIGINT NOT NULL,
    url VARCHAR(255) NOT NULL,
    CONSTRAINT fk_imagem_empreendimento FOREIGN KEY (fk_empreendimento) REFERENCES empreendimento (id) ON DELETE CASCADE,
    CONSTRAINT fk_imagem_tipo FOREIGN KEY (fk_tipo_imagem) REFERENCES tipo_imagem (id) ON DELETE CASCADE
);

-- Tabela de diferenciais dos empreendimentos
CREATE TABLE diferencial (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela de associação entre empreendimento e diferencial
CREATE TABLE diferencial_empreendimento (
    fk_empreendimento BIGINT NOT NULL,
    fk_diferencial BIGINT NOT NULL,
    PRIMARY KEY (
             fk_empreendimento,
             fk_diferencial
    ),
    CONSTRAINT fk_diferencial_empreendimento FOREIGN KEY (fk_empreendimento) REFERENCES empreendimento (id) ON DELETE CASCADE,
    CONSTRAINT fk_diferencial_empreendimento_diferencial FOREIGN KEY (fk_diferencial) REFERENCES diferencial (id) ON DELETE CASCADE
);

-- Tabela de visitas agendadas
CREATE TABLE agendamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_cliente BIGINT NOT NULL,
    fk_corretor BIGINT NOT NULL,
    fk_empreendimento BIGINT NOT NULL,
    duracao_minutos INT NOT NULL,
    data_agendamento DATETIME NOT NULL,
    status ENUM(
        'Agendado',
        'Concluído',
        'Cancelado'
    ) DEFAULT 'Agendado',
    data_inicio DATETIME,
    data_fim DATETIME,
    CONSTRAINT fk_agendamento_cliente FOREIGN KEY (fk_cliente) REFERENCES usuario (id),
    CONSTRAINT fk_agendamento_corretor FOREIGN KEY (fk_corretor) REFERENCES usuario (id),
    CONSTRAINT fk_agendamento_empreendimento FOREIGN KEY (fk_empreendimento) REFERENCES empreendimento (id)
);

-- Tabela de logs de ações do sistema
CREATE TABLE log(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fk_usuario BIGINT,
    origem VARCHAR(100) NOT NULL,
    detalhes VARCHAR(255) NOT NULL,
    data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_log_usuario FOREIGN KEY (fk_usuario) REFERENCES usuario (id)
);