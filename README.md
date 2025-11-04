# Tech Challenge - Sistema de Gestão de Restaurantes

[![Java](https:/---

## Funcionalidades

### Autenticaçãoshields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-Ready-2496ED.svg)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

> **Tech Challenge FIAP - ADJ**  
> Sistema para gestão de restaurantes, pedidos e clientes com autenticação JWT.

---

## Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Funcionalidades](#funcionalidades)
- [Arquitetura](#arquitetura)
- [Pré-requisitos](#pré-requisitos)
- [Instalação e Execução](#instalação-e-execução)
- [Configuração](#configuração)
- [Documentação da API](#documentação-da-api)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Modelo de Dados](#modelo-de-dados)
- [Testes](#testes)
- [Troubleshooting](#troubleshooting)
- [Autores](#autores)

---

## Sobre o Projeto

Sistema desenvolvido como parte do **Tech Challenge da FIAP** para gerenciar usuários de uma plataforma de restaurantes. 

O projeto está em desenvolvimento e atualmente oferece:

- **Sistema de Autenticação**: Login com JWT (JSON Web Tokens)
- **Gestão de Usuários**: CRUD completo de Clientes, Donos de Restaurante e Administradores
- **Gestão de Endereços**: Cadastro e gerenciamento de endereços vinculados aos usuários

O sistema implementa uma **arquitetura RESTful** com autenticação baseada em **JWT**

---

## Tecnologias

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.5** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **Spring Validation** - Validação de dados
- **Spring Actuator** - Monitoramento da aplicação

### Banco de Dados
- **MySQL 8.0** - Banco de dados relacional

### Documentação
- **SpringDoc OpenAPI 2.7.0** - Documentação automática da API (Swagger)

### Segurança
- **Auth0 Java JWT 4.4.0** - Geração e validação de tokens JWT

### Ferramentas e Utilitários
- **Lombok** - Redução de boilerplate
- **Docker & Docker Compose** - Containerização
- **Maven** - Gerenciamento de dependências

---

## ✨ Funcionalidades

### � Autenticação
- [x] Login com geração de token JWT
- [x] Autenticação baseada em Spring Security
- [x] Controle de acesso protegido por Bearer Token

### Gestão de Usuários
- [x] Listar todos os usuários
- [x] Buscar usuário por ID
- [x] Buscar usuários por nome
- [x] Atualizar senha do usuário

### Gestão de Clientes
- [x] Criar novo cliente
- [x] Listar todos os clientes
- [x] Buscar cliente por ID
- [x] Atualizar dados do cliente
- [x] Excluir cliente

### Gestão de Donos de Restaurante
- [x] Criar novo dono de restaurante
- [x] Listar todos os donos
- [x] Buscar dono por ID
- [x] Atualizar dados do dono
- [x] Excluir dono de restaurante

### Gestão de Administradores
- [x] Criar novo administrador
- [x] Listar todos os administradores
- [x] Buscar administrador por ID
- [x] Atualizar dados do administrador
- [x] Excluir administrador

### Gestão de Endereços
- [x] Criar endereço vinculado a usuário
- [x] Listar todos os endereços
- [x] Buscar endereço por ID
- [x] Atualizar endereço
- [x] Remover endereço de usuário

---

## Arquitetura

O projeto segue uma arquitetura em camadas:

```
┌─────────────────────────────────────┐
│         Controllers Layer           │  ◄── Endpoints REST
├─────────────────────────────────────┤
│          Services Layer             │  ◄── Lógica de Negócio
├─────────────────────────────────────┤
│        Repositories Layer           │  ◄── Acesso a Dados (JPA)
├─────────────────────────────────────┤
│          Domain Layer               │  ◄── Entidades e DTOs
└─────────────────────────────────────┘
            │
            ▼
    ┌───────────────┐
    │  MySQL 8.0    │
    └───────────────┘
```

### Padrões Utilizados

- **MVC (Model-View-Controller)** - Separação de responsabilidades
- **Repository Pattern** - Abstração do acesso a dados
- **DTO (Data Transfer Object)** - Transferência de dados entre camadas
- **Dependency Injection** - Inversão de controle com Spring
- **JOINED Inheritance Strategy** - Herança de usuários no banco de dados

---

## Pré-requisitos

Para executar a aplicação, você precisará ter instalado:

- [Docker](https://www.docker.com/get-started) (versão 20.10+)
- [Docker Compose](https://docs.docker.com/compose/install/) (versão 2.0+)

---

## Instalação e Execução

1. **Clone o repositório:**
```bash
git clone https://github.com/gf-filipe/tech-challenge-10adjt1.git
cd tech-challenge-10adjt1
```

2. **Execute com Docker Compose:**
```bash
docker-compose up --build -d
```

Este comando irá:
- Construir a imagem Docker da aplicação
- Criar e inicializar o banco de dados MySQL
- Executar o script de inicialização do banco
- Iniciar a aplicação na porta 8080

3. **Verifique se os containers estão rodando:**
```bash
docker-compose ps
```

4. **Acesse a aplicação:**
- API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/api/swagger-ui.html
- Health Check: http://localhost:8080/api/actuator/health

### Para parar a aplicação:
```bash
docker-compose down
```

### Para reiniciar a aplicação:
```bash
docker-compose restart
```

---

## Configuração

### Perfis de Ambiente

O projeto possui três perfis de configuração:

- **dev** (desenvolvimento) - `application-dev.properties`
- **prod** (produção) - `application-prod.properties`
- **default** - `application.properties`

Para alterar o perfil, modifique a variável:
```properties
spring.profiles.active=dev
```

### Variáveis de Ambiente Importantes

| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `SPRING_DATASOURCE_URL` | URL de conexão do MySQL | `jdbc:mysql://localhost:3306/techchallenge_dev` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco | `admin` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco | `admin` |
| `API_SECURITY_TOKEN_SECRET` | Chave secreta JWT | (definida em application.properties) |
| `SPRING_PROFILES_ACTIVE` | Perfil ativo | `dev` |

### Configuração JWT

O token JWT é configurado em `application.properties`:
```properties
api.security.token.secret=U7x!A%D*G-KaPdSgVkYp3s6v9y$B&E)H+MbQeThWmZq4t7w!z%C*F-J@NcRf
```


---

## Documentação da API

A documentação completa da API está disponível via **Swagger UI** após iniciar a aplicação:

**http://localhost:8080/api/swagger-ui.html**

### Principais Endpoints

#### Autenticação
```
POST /api/auth/v1/login      - Login de usuário (retorna token JWT)
```

**Exemplo de requisição de login:**
```json
{
  "email": "usuario@email.com",
  "senha": "123456"
}
```

#### Usuários
```
GET    /api/v1/usuario              - Listar todos os usuários
GET    /api/v1/usuario/{id}         - Buscar usuário por ID
GET    /api/v1/usuario/busca-nome   - Buscar usuários por nome
PATCH  /api/v1/usuario/{id}         - Atualizar senha do usuário
```

#### Clientes
```
GET    /api/v1/cliente           - Listar todos os clientes
GET    /api/v1/cliente/{id}      - Buscar cliente por ID
POST   /api/v1/cliente           - Criar novo cliente
PUT    /api/v1/cliente/{id}      - Atualizar cliente
DELETE /api/v1/cliente/{id}      - Excluir cliente
```

#### Donos de Restaurante
```
GET    /api/v1/dono-restaurante           - Listar todos os donos
GET    /api/v1/dono-restaurante/{id}      - Buscar dono por ID
POST   /api/v1/dono-restaurante           - Criar novo dono
PUT    /api/v1/dono-restaurante/{id}      - Atualizar dono
DELETE /api/v1/dono-restaurante/{id}      - Excluir dono
```

#### Administradores
```
GET    /api/v1/admin           - Listar todos os administradores
GET    /api/v1/admin/{id}      - Buscar administrador por ID
POST   /api/v1/admin           - Criar novo administrador
PUT    /api/v1/admin/{id}      - Atualizar administrador
DELETE /api/v1/admin/{id}      - Excluir administrador
```

#### Endereços
```
GET    /api/v1/endereco                    - Listar todos os endereços
GET    /api/v1/endereco/{id}               - Buscar endereço por ID
POST   /api/v1/endereco/{id_user}          - Criar endereço para usuário
PUT    /api/v1/endereco/{id}               - Atualizar endereço
DELETE /api/v1/endereco/{id_user}/{id}     - Remover endereço de usuário
```

### Autenticação nas Requisições

Após o login, inclua o token JWT no header das requisições:

```
Authorization: Bearer {seu-token-jwt}
```

---

## Estrutura do Projeto

```
tech-challenge-10adjt1/
│
├── src/
│   ├── main/
│   │   ├── java/br/com/fiap/techchallenge/
│   │   │   ├── config/              # Configurações (Security, OpenAPI)
│   │   │   ├── controllers/         # Endpoints REST
│   │   │   ├── domain/              # Entidades e DTOs
│   │   │   ├── exceptions/          # Tratamento de exceções
│   │   │   ├── repositories/        # Interfaces JPA
│   │   │   ├── services/            # Lógica de negócio
│   │   │   └── TechchallengeApplication.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       └── application-prod.properties
│   │
│   └── test/                        # Testes unitários e integração
│
├── initdb.d/                        # Scripts de inicialização do BD
│   └── Script Tech_ChallengeV3.sql
│
├── docker-compose.yml               # Configuração Docker (produção)
├── docker-compose-dev.yml           # Configuração Docker (desenvolvimento)
├── Dockerfile                       # Imagem Docker da aplicação
├── pom.xml                          # Dependências Maven
└── README.md                        # Este arquivo
```

---

## Modelo de Dados

### Estratégia de Herança - JOINED

O sistema utiliza a estratégia **JOINED** para herança de usuários:

```
                    ┌──────────────┐
                    │   usuario    │
                    ├──────────────┤
                    │ id (PK)      │
                    │ nome         │
                    │ email        │
                    │ senha        │
                    │ id_endereco  │
                    └──────┬───────┘
                           │
        ┌──────────────────┼──────────────────┐
        │                  │                  │
   ┌────▼────┐      ┌──────▼──────┐     ┌────▼────┐
   │ cliente │      │ dono_rest.  │     │  admin  │
   └─────────┘      └─────────────┘     └─────────┘
```

### Principais Entidades

- **usuario**: Tabela pai com dados comuns (nome, email, senha, endereço)
- **cliente**: Herda de usuario - Usuários que podem fazer pedidos
- **dono_restaurante**: Herda de usuario - Proprietários de restaurantes  
- **admin**: Herda de usuario - Administradores do sistema
- **endereco**: Endereços vinculados aos usuários


### Diagrama ER Completo

Para visualizar o diagrama completo, consulte o arquivo:
```
initdb.d/Script Tech_ChallengeV3.sql
```

---

## Testes

### Executar testes:

```bash
# Com Maven Wrapper
./mvnw test

# Com Maven instalado
mvn test

# Com cobertura
./mvnw clean test jacoco:report
```

### Estrutura de Testes

- **Testes Unitários**: Testam componentes isolados
- **Testes de Integração**: Testam fluxos completos

---


## Troubleshooting

### Erro: "Port 8080 already in use"
```bash
# Linux/Mac
lsof -i :8080
kill -9 <PID>

# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Erro de conexão com o banco de dados
Verifique se o MySQL está rodando:
```bash
docker-compose ps db
```

Verifique os logs:
```bash
docker-compose logs db
```

### Erro: "Access denied for user"
Verifique as credenciais em `application.properties` ou nas variáveis de ambiente.

---

## Autores

Desenvolvido como parte do **Tech Challenge da FIAP - ADJ**.

**Repositório**: [tech-challenge-10adjt1](https://github.com/gf-filipe/tech-challenge-10adjt1)

**Equipe:**
- Filipe ([gf-filipe](https://github.com/gf-filipe))
- Lucas Escolástico ([lucasescol](https://github.com/lucasescol))
- Leandro Recife ([CabeloSG](https://github.com/CabeloSG))
- Alex Sousa Alves ([AlexSousaAlvess](https://github.com/AlexSousaAlvess))
- Lucas Souza ([LucasSouza1407](https://github.com/LucasSouza1407))

---
