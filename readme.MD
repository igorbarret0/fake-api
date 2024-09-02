<h1>Projeto de Integração com API Externa usando Spring Boot </h1>

Este projeto é uma aplicação Spring Boot que consome uma API externa utilizando Feign Client em conjunto com o Load Balancer. O sistema é projetado para gerenciar produtos de forma eficiente, oferecendo um CRUD completo, incluindo a capacidade de salvar produtos provenientes da API externa e adicionar novos produtos manualmente.

Funcionalidades Principais:

Feign Client com Load Balancer:Integração robusta com APIs externas, garantindo distribuição de carga e alta disponibilidade.

Swagger: Documentação automática da API, facilitando o teste e a compreensão dos endpoints disponíveis.

Tratamento de Exceções: Mecanismo eficaz para lidar com erros, garantindo uma experiência de usuário suave e mensagens de erro claras.

## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [MySQL](https://www.mysql.com/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Feign Client](https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/)
- [Swagger](https://swagger.io/)


## Como Executar

- Clonar repositório git
```
git clone git@github.com:igorbarret0/fake-api.git
```

- Construir o projeto:
```
./mvnw clean package
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).

## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Postman](https://www.postman.com/)

-  PRODUCTS
```
POST /products/api - Registrar os produtos advindos da API externa
```

```
POST /products - Registrar um novo produto manualmente

{
    {
    "title": "new product",
    "price": 20.0,
    "category": "new category",
    "description": "some description",
    "image": "some image"
}
```

```
PUT /products/{id} - Atualizar um produto

{
    "title": "new product updated",
    "price": 20.0,
    "category": "new category updated",
    "description": "some description",
    "image": "some image"
}
```

```
GET /products - Recuperar todos os produtos
```

```
GET /products/{title} - Recuperar um produto pelo título

{
    "title": "new product updated",
    "price": 20.0,
    "category": "new category updated",
    "description": "some description",
    "image": "some image"
}
```

```
DELETE /products/{title} - Deletar um produto pelo título
```



