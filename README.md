# Mobiauto Backend

Projeto de backend em **Java 11** com **Spring Boot** e **MongoDB**, utilizando autenticação JWT.
Intuito criar um sistema de revenda de carros basico contando com:
- Cadastro de Usuario também utilizado para separação de acesso a aplicação;
- Cadastro de Lojas que liga usuarios a oportundiades especificas por lojas;
- Cadastro de Oportunidade de revenda contendo principais informações como Cliente e Veiculo;
- Cadastro de Revenda;

---

## Tecnologias
- Java 11
- Spring Boot
- Spring Security + JWT
- MongoDB
- Docker + Docker Compose

---

## Documentação Disponivel
- Collection Postman no path "src.main.resources.mobiauto-backend.postman_collection.json"
- Swagger disponivel no path "http://localhost:8080/swagger-ui/index.html#"


## Como rodar com Docker

1. Clone o repositório:
   ```bash
   git clone https://github.com/renan2105/mobiauto-backend-202502
   cd mobiauto-backend
   docker-compose up --build
