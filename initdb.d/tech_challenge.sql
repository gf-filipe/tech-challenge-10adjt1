CREATE DATABASE IF NOT EXISTS Tech_Challenge;
-- DROP DATABASE Tech_Challenge;

USE Tech_Challenge;

-- Criar a tabela UsuarioTipo (com os tipos de usuários)
CREATE TABLE IF NOT EXISTS UsuarioTipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipoUsuario VARCHAR(255) UNIQUE
);

-- Inserir os tipos de usuário na tabela UsuarioTipo
INSERT INTO UsuarioTipo (tipoUsuario) VALUES ('Cliente');
INSERT INTO UsuarioTipo (tipoUsuario) VALUES ('Dono Restaurante');
INSERT INTO UsuarioTipo (tipoUsuario) VALUES ('Admin');

-- Criar a tabela Endereco
CREATE TABLE IF NOT EXISTS Endereco (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rua VARCHAR(255),
    numero VARCHAR(50),
    cidade VARCHAR(255),
    cep VARCHAR(20),
    complemento VARCHAR(255)
);

-- Criar a tabela Usuario
CREATE TABLE IF NOT EXISTS Usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255),
    dataUltimaAlteracao DATE,
    idTipoUsuario INT,
    FOREIGN KEY (idTipoUsuario) REFERENCES UsuarioTipo(id)
);

-- Criar a tabela Cliente_Endereco (relacionamento muitos-para-muitos)
CREATE TABLE IF NOT EXISTS Cliente_Endereco (
    idCliente BIGINT,
    idEndereco BIGINT,
    PRIMARY KEY (idCliente, idEndereco),
    FOREIGN KEY (idCliente) REFERENCES Usuario(id),
    FOREIGN KEY (idEndereco) REFERENCES Endereco(id)
);

-- Criar a tabela TipoRestaurante (para categorizar os tipos de restaurante)
CREATE TABLE IF NOT EXISTS TipoRestaurante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100) UNIQUE
);

-- Inserir tipos de restaurante na tabela TipoRestaurante com mais opções
INSERT INTO TipoRestaurante (tipo) VALUES 
('Fast Food'),
('Casual Dining'),
('Fine Dining'),
('Buffet'),
('Comida de Rua'),
('Comida Caseira'),
('Marmitex'),
('Café'),
('Árabe'),
('Italiana'),
('Chinesa'),
('Japonesa'),
('Mexicana'),
('Brasileira'),
('Francesa'),
('Indiana'),
('Vegetariana'),
('Vegan'),
('Mediterrânea'),
('Peruana'),
('Portuguesa'),
('Africana'),
('Coreana'),
('Sushi Bar'),
('Steakhouse'),
('Hamburgueria'),
('Pizzaria'),
('Comida Regional'),
('Grelhados'),
('Peixes e Frutos do Mar');

-- Criar a tabela StatusRestaurante
CREATE TABLE IF NOT EXISTS StatusRestaurante (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) UNIQUE
);

-- Inserir status na tabela StatusRestaurante
INSERT INTO StatusRestaurante (status) VALUES 
('Aberto'),
('Fechado'),
('Em Reformas'),
('Temporariamente Fechado');

-- Criar a tabela Restaurante
CREATE TABLE IF NOT EXISTS Restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idUsuario BIGINT,
    idStatusRestaurante INT,
    telefone VARCHAR(20),  -- Telefone para contato
    email VARCHAR(255),  -- E-mail de contato
    site VARCHAR(255),  -- URL do site
    descricao VARCHAR(500),
    imagemUrl VARCHAR(255),  -- URL da imagem representativa
    horaAbertura TIME,
    horaFechamento TIME,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(id),
    FOREIGN KEY (idStatusRestaurante) REFERENCES StatusRestaurante(id)
);

-- Criar a tabela de relacionamento entre Restaurante e TipoRestaurante (muitos-para-muitos)
CREATE TABLE IF NOT EXISTS Restaurante_Tipo (
    idRestaurante BIGINT,
    idTipoRestaurante INT,
    PRIMARY KEY (idRestaurante, idTipoRestaurante),
    FOREIGN KEY (idRestaurante) REFERENCES Restaurante(id),
    FOREIGN KEY (idTipoRestaurante) REFERENCES TipoRestaurante(id)
);

-- Criar a tabela Restaurante_Endereco (relacionamento muitos-para-muitos entre Restaurante e Endereco)
CREATE TABLE IF NOT EXISTS Restaurante_Endereco (
    idRestaurante BIGINT,
    idEndereco BIGINT,
    PRIMARY KEY (idRestaurante, idEndereco),
    FOREIGN KEY (idRestaurante) REFERENCES Restaurante(id),
    FOREIGN KEY (idEndereco) REFERENCES Endereco(id)
);


-- Criar a tabela TipoCardapio (para categorizar os tipos de cardápio)
CREATE TABLE IF NOT EXISTS TipoCardapio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(100)
);

-- Inserir os tipos de cardápio na tabela TipoCardapio com mais opções
INSERT INTO TipoCardapio (tipo)
VALUES 
('Vegano'),
('Vegetariano'),
('Carne'),
('Frutos do Mar'),
('Sobremesa'),
('Bebidas'),
('Low Carb'),
('Gluten-Free'),
('Sem Lactose'),
('Orgânico'),
('Keto'),
('Fit'),
('Gourmet'),
('Diet'),
('Pizza'),
('Sanduíches'),
('Petiscos'),
('Sopas'),
('Culinária Internacional');

-- Criar a tabela Cardapio com a referência ao TipoCardapio
CREATE TABLE IF NOT EXISTS Cardapio (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idRestaurante BIGINT,
    idTipoCardapio INT,
    nome VARCHAR(255),
    preco DECIMAL(10,2),
    FOREIGN KEY (idRestaurante) REFERENCES Restaurante(id),
    FOREIGN KEY (idTipoCardapio) REFERENCES TipoCardapio(id)
);

-- Criar a tabela TipoPedido
CREATE TABLE IF NOT EXISTS TipoPedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) UNIQUE
);

-- Inserir dados na tabela TipoPedido
INSERT INTO TipoPedido (tipo)
VALUES 
('PEDIDO'),
('VENDA'),
('REEMBOLSO');

-- Criar a tabela StatusPedido (para categorizar os status dos pedidos)
CREATE TABLE IF NOT EXISTS StatusPedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) UNIQUE
);

-- Inserir status de pedidos na tabela StatusPedido
INSERT INTO StatusPedido (status)
VALUES 
('Pendente'),
('Em Preparação'),
('Finalizado'),
('Cancelado'),
('Em Andamento'),
('Aguardando Pagamento'),
('Pagamento Confirmado'),
('Entregue'),
('Em Transporte'),
('Devolvido');

-- Criar a tabela Pedido com referência ao StatusPedido
CREATE TABLE IF NOT EXISTS Pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    statusPedido INT,  -- Referência ao status do pedido
    idCliente BIGINT,
    idRestaurante BIGINT,
    idCardapio BIGINT,
    idEndereco BIGINT,
    FOREIGN KEY (idCliente) REFERENCES Usuario(id),
    FOREIGN KEY (idRestaurante) REFERENCES Restaurante(id),
    FOREIGN KEY (idCardapio) REFERENCES Cardapio(id),
    FOREIGN KEY (idEndereco) REFERENCES Endereco(id),
    FOREIGN KEY (statusPedido) REFERENCES StatusPedido(id)  -- Chave estrangeira para StatusPedido
);

-- Criar a tabela Historico
CREATE TABLE IF NOT EXISTS Historico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idTipoPedido INT,
    dataPedido DATE,
    idUsuario BIGINT,idPedido BIGINT,
    FOREIGN KEY (idUsuario) REFERENCES Usuario(id),
    FOREIGN KEY (idTipoPedido) REFERENCES TipoPedido(id),
    FOREIGN KEY (idPedido) REFERENCES Pedido(id)
);