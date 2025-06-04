# 🔍 API Código
A API Código é uma API REST desenvolvida em Java com Spring Boot que permite gerar automaticamente o código de uma classe Java a partir de parâmetros como:

✅ Nome da classe

✅ Atributos

✅ Herança (opcional)

✅ Tipo de encapsulamento (public, private, etc.)

Além disso, a API salva um histórico de códigos gerados, permitindo consultar versões anteriores.

## 🔥 Tecnologias Utilizadas
✔️ Java 17

✔️ Spring Boot 3.2+

✔️ Spring Data JPA

✔️ PostgreSQL

✔️ Docker (opcional)

✔️ Swagger (Documentação interativa)

✔️ Maven

✔️ Redis

✔️ RabbitMQ

## 🎯 Funcionalidades

✅ Gerar código Java para classes personalizadas

✅ Suporte a atributos, encapsulamento e herança

✅ Persistência no banco de dados

✅ Endpoint de histórico de códigos gerados

✅ API REST documentada com Swagger

➡️ Swagger: http://localhost:8080/swagger-ui/index.html

## ⚙️ Como Executar o Projeto

###✅ Pré-requisitos

Java 17 ou superior

Maven 3.8+

PostgreSQL rodando localmente ou via Docker

Git instalado (opcional)

Redis para cache com spring cache

RabbitMQ para mensageria

## 🚀 Passos para rodar localmente

Clone o repositório:

git clone https://github.com/wesleycipriano1/api-codigo.git


### Configure o banco de dados:

Crie um database PostgreSQL 

Crie um database Redis

RabbitMQ via Docker ou cloudAMQP

Atualize as credenciais no application.properties e crie um .env para variaveis de ambiente 

### Execute o projeto:

mvn spring-boot:run

Acesse a documentação:

http://localhost:8080/swagger-ui/index.html

### 🛠️ Melhorias Planejadas

🔍 Observabilidade com Spring Boot Actuator + Micrometer + Prometheus + Grafana

🐘 Migrations com Flyway


🌍 Suporte a geração de código para outras linguagens (Python, C#, etc.)

🚀 Deploy em nuvem com Docker e Kubernetes (no momento em deploy no render)

## 🤝 Contribuição

Contribuições são bem-vindas!

Sinta-se livre para:

Abrir issues

Enviar pull requests

Sugerir melhorias

## 📝 Licença
Este projeto está licenciado sob a licença MIT 

## 👨‍💻 Desenvolvedor

Feito com 💙 por Wesley Cipriano

#### Contado : www.linkedin.com/in/wesley-silva-66070a247

⭐ Se você gostou do projeto, deixe uma estrela no repositório! ⭐
