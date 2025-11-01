-- O docker-compose já se encarrega de criar o banco de dados.
-- CREATE DATABASE IF NOT EXISTS techchallenge-dev;
-- USE techchallenge-dev;

--
-- Tabela `endereco` (Sem alterações)
--
CREATE TABLE IF NOT EXISTS endereco (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(255),
    numero VARCHAR(50),
    cidade VARCHAR(100),
    cep VARCHAR(20),
    complemento VARCHAR(255)
);

--
-- ATUALIZAÇÃO: Tabela `usuario` agora é a tabela Pai na estratégia JOINED
-- Propósito: Armazena os dados comuns a todos os tipos de usuários.
-- A coluna `id_tipo_usuario` foi removida, pois o tipo é definido pela tabela filha.
--
CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_endereco BIGINT,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    data_criacao DATETIME,
    data_ultima_alteracao DATETIME,
    FOREIGN KEY (id_endereco) REFERENCES endereco(id)
);

--
-- NOVO: Tabelas filhas para a estratégia JOINED
-- A existência de um ID de usuário em uma dessas tabelas define seu tipo.
--
CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dono_restaurante (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS admin (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES usuario(id) ON DELETE CASCADE
);

--
-- Tabelas de Restaurante (Com FK ajustada)
--
CREATE TABLE IF NOT EXISTS tipo_restaurante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS status_restaurante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- ATUALIZAÇÃO: A FK agora aponta para a tabela específica `dono_restaurante`.
    id_dono_restaurante BIGINT NOT NULL,
    id_status_restaurante INT,
    nome VARCHAR(255) NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(255),
    descricao TEXT,
    hora_abertura TIME,
    hora_fechamento TIME,
    FOREIGN KEY (id_dono_restaurante) REFERENCES dono_restaurante(id),
    FOREIGN KEY (id_status_restaurante) REFERENCES status_restaurante(id)
);

CREATE TABLE IF NOT EXISTS restaurante_endereco (
    id_restaurante BIGINT,
    id_endereco BIGINT,
    PRIMARY KEY (id_restaurante, id_endereco),
    FOREIGN KEY (id_restaurante) REFERENCES restaurante(id),
    FOREIGN KEY (id_endereco) REFERENCES endereco(id)
);

CREATE TABLE IF NOT EXISTS restaurante_tipo (
    id_restaurante BIGINT,
    id_tipo_restaurante INT,
    PRIMARY KEY (id_restaurante, id_tipo_restaurante),
    FOREIGN KEY (id_restaurante) REFERENCES restaurante(id),
    FOREIGN KEY (id_tipo_restaurante) REFERENCES tipo_restaurante(id)
);

--
-- Tabelas de Pedido (Com FK ajustada)
--
CREATE TABLE IF NOT EXISTS cardapio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_restaurante BIGINT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_restaurante) REFERENCES restaurante(id)
);

CREATE TABLE IF NOT EXISTS status_pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- ATUALIZAÇÃO: A FK agora aponta para a tabela específica `cliente`.
    id_cliente BIGINT NOT NULL,
    id_restaurante BIGINT NOT NULL,
    id_endereco_entrega BIGINT NOT NULL,
    id_status_pedido INT NOT NULL,
    data_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    FOREIGN KEY (id_restaurante) REFERENCES restaurante(id),
    FOREIGN KEY (id_endereco_entrega) REFERENCES endereco(id),
    FOREIGN KEY (id_status_pedido) REFERENCES status_pedido(id)
);

CREATE TABLE IF NOT EXISTS pedido_item (
    id_pedido BIGINT,
    id_cardapio BIGINT,
    quantidade INT NOT NULL DEFAULT 1,
    preco_unitario DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id_pedido, id_cardapio),
    FOREIGN KEY (id_pedido) REFERENCES pedido(id),
    FOREIGN KEY (id_cardapio) REFERENCES cardapio(id)
);

--
-- Tabela de Histórico (Sem alterações de estrutura)
--
CREATE TABLE IF NOT EXISTS tipo_historico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS historico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario BIGINT NOT NULL,
    id_pedido BIGINT NOT NULL,
    id_tipo_historico INT NOT NULL,
    data DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id),
    FOREIGN KEY (id_pedido) REFERENCES pedido(id),
    FOREIGN KEY (id_tipo_historico) REFERENCES tipo_historico(id)
);


-- =================================================================================
-- INSERTS ATUALIZADOS PARA O MODELO JOINED
-- =================================================================================
INSERT INTO tipo_restaurante (tipo) VALUES ('MASSA'),('VEGANO'),('TEMÁTICO'),('FAMILIAR'),('JAPONESA'),('BRASILEIRA'),('PIZZARIA'),('HAMBURGUERIA');
INSERT INTO status_restaurante (status) VALUES ('ABERTO'),('FECHADO'),('TEMPORARIAMENTE FECHADO');
INSERT INTO status_pedido (status) VALUES ('PENDENTE'),('FINALIZADO'),('CANCELADO'),('EM_PREPARACAO'),('ENTREGUE');
INSERT INTO tipo_historico (tipo) VALUES ('PEDIDO'), ('VENDA');

INSERT INTO endereco (rua, numero, cidade, cep, complemento) VALUES
('Rua dos Admins', '10', 'São Paulo', '01000-001', 'Bloco A'),
('Avenida dos Negócios', '200', 'Rio de Janeiro', '20000-002', 'Sala 50'),
('Praça da Liberdade', '30', 'Belo Horizonte', '30140-010', NULL),
('Rua das Flores', '45', 'Curitiba', '80010-010', 'Apto 101'),
('Rua Principal da Pizza', '1000', 'São Paulo', '05425-070', 'Loja 1'),
('Avenida Saborosa', '550', 'São Paulo', '04538-132', 'Filial Itaim');

-- 1. Inserindo dados na tabela PAI `usuario`, a senha é 123456 e está criptografada
INSERT INTO usuario (id, nome, email, senha, data_criacao, data_ultima_alteracao, id_endereco) VALUES
(1, 'Admin Master', 'admin@techchallenge.com', '$2a$10$52q6BoLUpbuq/2XylZL8JulkJUFQiWg4kNNIKPXBFr6GI3n/0XPvy', NOW(), NOW(), 1),
(2, 'Carlos Pereira', 'carlos.dono@email.com', '$2a$10$52q6BoLUpbuq/2XylZL8JulkJUFQiWg4kNNIKPXBFr6GI3n/0XPvy', NOW(), NOW(), 2),
(3, 'Ana Silva', 'ana.cliente@email.com', '$2a$10$52q6BoLUpbuq/2XylZL8JulkJUFQiWg4kNNIKPXBFr6GI3n/0XPvy', NOW(), NOW(), 3),
(4, 'Bruno Costa', 'bruno.cliente@email.com', '$2a$10$52q6BoLUpbuq/2XylZL8JulkJUFQiWg4kNNIKPXBFr6GI3n/0XPvy', NOW(), NOW(), 4);

-- 2. NOVO: Inserindo os IDs nas tabelas FILHAS para definir o tipo de cada usuário
INSERT INTO admin (id) VALUES (1);
INSERT INTO dono_restaurante (id) VALUES (2);
INSERT INTO cliente (id) VALUES (3);
INSERT INTO cliente (id) VALUES (4);

-- 3. Inserindo dados nas outras tabelas (sem alterações no conteúdo, apenas nas FKs que já foram ajustadas acima)
INSERT INTO restaurante (nome, descricao, telefone, email, hora_abertura, hora_fechamento, id_dono_restaurante, id_status_restaurante) VALUES
('Pizzaria do Carlos', 'A melhor pizza da cidade, com massa artesanal.', '11999998888', 'contato@pizzadocarlos.com', '18:00:00', '23:30:00', 2, 1);

INSERT INTO restaurante_endereco (id_restaurante, id_endereco) VALUES (1, 5), (1, 6);
INSERT INTO restaurante_tipo (id_restaurante, id_tipo_restaurante) VALUES (1, 7), (1, 1);

INSERT INTO cardapio (nome, preco, id_restaurante) VALUES
('Pizza Margherita', 45.50, 1),
('Pizza Calabresa', 52.00, 1),
('Refrigerante Lata', 8.00, 1),
('Tiramisu', 22.50, 1);

INSERT INTO pedido (id_cliente, id_restaurante, id_endereco_entrega, id_status_pedido) VALUES
(3, 1, 3, 5),
(4, 1, 4, 4);

INSERT INTO pedido_item (id_pedido, id_cardapio, quantidade, preco_unitario) VALUES
(1, 1, 1, 45.50),
(1, 3, 2, 8.00),
(2, 2, 2, 52.00);

INSERT INTO historico (id_usuario, id_pedido, id_tipo_historico) VALUES
(3, 1, 1),
(2, 1, 2),
(4, 2, 1),
(2, 2, 2);


-- =================================================================================
-- CONSULTAS ATUALIZADAS PARA O MODELO JOINED
-- =================================================================================

-- ATUALIZAÇÃO Consulta 1: Listar Todos os Usuários e Seus Perfis
-- Agora usamos LEFT JOINs nas tabelas filhas para descobrir o perfil de cada usuário.
SELECT
	u.id,
    u.nome,
    u.email,
	u.senha,
	u.data_criacao,
    u.data_ultima_alteracao,
    CASE
        WHEN c.id IS NOT NULL THEN 'CLIENTE'
        WHEN dr.id IS NOT NULL THEN 'DONO_RESTAURANTE'
        WHEN a.id IS NOT NULL THEN 'ADMIN'
        ELSE 'INDEFINIDO'
    END AS perfil
FROM
    usuario u
LEFT JOIN
    cliente c ON u.id = c.id
LEFT JOIN
    dono_restaurante dr ON u.id = dr.id
LEFT JOIN
    admin a ON u.id = a.id;

-- Consulta 2: (Sem alterações)
SELECT c.nome AS item, c.preco FROM cardapio c JOIN restaurante r ON c.id_restaurante = r.id WHERE r.nome = 'Pizzaria do Carlos';

-- ATUALIZAÇÃO Consulta 3: Detalhes de um Pedido Específico
-- O JOIN para buscar o nome do cliente agora passa pela tabela `cliente`.
SELECT
    p.id AS pedido_id, p.data_pedido, u.nome AS cliente, r.nome AS restaurante,
    sp.status AS status_do_pedido, CONCAT(e.rua, ', ', e.numero) AS endereco_entrega,
    ci.nome AS item_cardapio, pi.quantidade, pi.preco_unitario,
    (pi.quantidade * pi.preco_unitario) AS subtotal_item
FROM pedido p
JOIN cliente c ON p.id_cliente = c.id
JOIN usuario u ON c.id = u.id
JOIN restaurante r ON p.id_restaurante = r.id
JOIN endereco e ON p.id_endereco_entrega = e.id
JOIN status_pedido sp ON p.id_status_pedido = sp.id
JOIN pedido_item pi ON p.id = pi.id_pedido
JOIN cardapio ci ON pi.id_cardapio = ci.id
WHERE p.id = 1;

-- ATUALIZAÇÃO Consulta 4: Calcular o Valor Total de Cada Pedido
-- O JOIN para buscar o nome do cliente também foi ajustado.
SELECT
    p.id AS pedido_id, u.nome AS cliente, p.data_pedido,
    SUM(pi.quantidade * pi.preco_unitario) AS valor_total
FROM pedido p
JOIN pedido_item pi ON p.id = pi.id_pedido
JOIN cliente c ON p.id_cliente = c.id
JOIN usuario u ON c.id = u.id
GROUP BY p.id, u.nome, p.data_pedido
ORDER BY p.id;

-- ATUALIZAÇÃO Consulta 5: Listar Todos os Restaurantes com Suas Categorias e Endereços
-- O JOIN para buscar o nome do dono agora passa pela tabela `dono_restaurante`.
SELECT
    r.nome AS restaurante, u.nome AS dono,
    GROUP_CONCAT(DISTINCT tr.tipo SEPARATOR ', ') AS categorias,
    GROUP_CONCAT(DISTINCT CONCAT(e.rua, ', ', e.numero) SEPARATOR ' | ') AS filiais
FROM restaurante r
JOIN dono_restaurante dr ON r.id_dono_restaurante = dr.id
JOIN usuario u ON dr.id = u.id
LEFT JOIN restaurante_tipo rt ON r.id = rt.id_restaurante
LEFT JOIN tipo_restaurante tr ON rt.id_tipo_restaurante = tr.id
LEFT JOIN restaurante_endereco re ON r.id = re.id_restaurante
LEFT JOIN endereco e ON re.id_endereco = e.id
GROUP BY r.id, u.nome;

-- ATUALIZAÇÃO Consulta 6: Histórico de Vendas de um Dono de Restaurante
-- O JOIN para buscar o nome do cliente foi ajustado. O JOIN para o dono já usava `historico.id_usuario` e pode ser mantido.
SELECT
    h.data, th.tipo AS tipo_evento, p.id AS pedido_id,
    u_cliente.nome AS cliente,
    (SELECT SUM(pi.quantidade * pi.preco_unitario) FROM pedido_item pi WHERE pi.id_pedido = p.id) AS valor_da_venda
FROM historico h
JOIN usuario u_dono ON h.id_usuario = u_dono.id
JOIN tipo_historico th ON h.id_tipo_historico = th.id
JOIN pedido p ON h.id_pedido = p.id
JOIN cliente c ON p.id_cliente = c.id
JOIN usuario u_cliente ON c.id = u_cliente.id
WHERE u_dono.nome = 'Carlos Pereira' AND th.tipo = 'VENDA'
ORDER BY h.data DESC;

-- Consulta 7: (Sem alterações)
SELECT 
	c.nome AS item, 
	SUM(pi.quantidade) AS total_vendido 
FROM pedido_item pi 
JOIN cardapio c ON pi.id_cardapio = c.id 
JOIN restaurante r ON c.id_restaurante = r.id 
WHERE r.nome = 'Pizzaria do Carlos' GROUP BY c.nome ORDER BY total_vendido DESC;