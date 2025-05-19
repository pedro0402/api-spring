# 📚 API Spring - Biblioteca

[![Java Version](https://img.shields.io/badge/Java-21-blue.svg)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-green.svg)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-✓-blue.svg)](https://www.docker.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)

API RESTful desenvolvida em Java com Spring Boot para gerenciamento de usuários em uma biblioteca. O projeto inclui autenticação via OAuth2 com um servidor de autenticação próprio, autenticação via Google, integração com PostgreSQL, documentação automática com Swagger/OpenAPI e suporte a containers Docker.

## 📋 Pré-requisitos

- Java 21 JDK instalado
- Maven 3.8+
- Docker 20.10+
- PostgreSQL 15 (ou container Docker)
- Postman (opcional para testes)

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.1.5
- Spring Security
- Spring Data JPA
- MapStruct
- PostgreSQL 15
- Maven
- Docker
- SLF4J (Logback)
- Postman
- Swagger/OpenAPI (via springdoc-openapi)

## 🧩 Funcionalidades

- Cadastro, atualização, listagem e exclusão de autores e clientes
- Filtro de pesquisa por nome e nacionalidade
- Autenticação e autorização com OAuth2
- Documentação interativa da API via Swagger
- Logs estruturados com SLF4J
- Deploy com Docker multi-stage

## 📁 Estrutura do Projeto

```
api-spring/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/pedro/jpa/libraryapi/
│ │ │ ├── controller/
│ │ │ ├── dto/
│ │ │ ├── mappers/
│ │ │ ├── model/
│ │ │ ├── service/
│ │ │ └── config/
│ │ └── resources/
│ │ ├── application.yml
│ │ └── logback-spring.xml
├── Dockerfile
├── pom.xml
```

## 🐳 Como Executar com Docker
1. Build da imagem:
```bash
docker build -t pedrohenrique1910/libraryapi .
```
2. Executar o container:
```bash
docker run -p 8080:8080 pedrohenrique1910/libraryapi
```
3. Acessar a documentação Swagger:
```bash
http://localhost:8080/swagger-ui.html
```
---

## 🔐 Autenticação
O projeto inclui um servidor de autenticação (Auth Server) que emite tokens JWT para acesso à API. As credenciais e configurações estão definidas no arquivo no SecurityConfig.java. Além disso, o projeto possui autenticação com Google.

---

## 🧪 Testes e Desenvolvimento
Utilize o Postman para testar os endpoints da API.

Logs detalhados são gerados com SLF4J para facilitar o monitoramento e depuração.

---

