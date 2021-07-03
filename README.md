# Product Order Assignment

Run
--
In order to start the service, you need to build first:

    ./gradlew clean build

Then start with docker-compose:

    docker-compose up -d

Then you can access to the service with following base URL:

    http://localhost:8080

Features
---

- Add/Update Product & Get All products
- Create order & Get orders by given time range

## API

Swagger-UI
---
Swagger-UI is available in the project. You can access it via the following URL:

    http://localhost:8080/swagger-ui.html

OpenAPI 3.0 Spec
---

You can find the OpenAPI 3.0 spec under api folder.

Answers
---

- We can introduce OAuth2 security by adding just a few libraries and configuration classes.
- Not sure what is meant by `redundant` but we can split this service into two microservices, ProductService and
  OrderService. Splitting won't be too hard since domains are splitted.