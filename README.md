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

### Create Product:

#### Request

    POST /api/v1/products
    Content-Type: application/json

    {
      "name": "product name",
      "price": 0.5
    }

#### Responses

    204 CREATED

    {
      "id": "cec795c3-20b9-4b29-9146-e2a8071edcf3",
      "name": "product name",
      "price": 0.5,
      "createdAt": "2021-07-03T14:01:29.156368700Z"
    }

---

    400 Bad Request
    When input is not readable or price is not greater than 0

### Update Product

#### Request

    PUT /api/v1/products/{id}
    Content-Type: application/json
    
    {
      "name": "new product name",
      "price": 1.5
    }

#### Responses

    200 OK
    
    {
      "id": "cec795c3-20b9-4b29-9146-e2a8071edcf3",
      "name": "product name",
      "price": 0.5,
      "createdAt": "2021-07-03T14:01:29.156368700Z"
    }

---

    400 Bad Request    
    When input is not readable or price is not greater than 0

---

    404 Not Found
    When product with given ID not found

### Get Products

#### Request

    GET /api/v1/products?page={page}&size={size}&sort={sort}
    
    page: Zero-based page index (0..N). Default 0
    size: The size of the page to be returned. Default 20
    sort: Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported. e.g. createdAt,DESC

#### Response

    200 OK
  
    {
      "content": [
          {
              "id": "9ff102e0-9d1a-4285-988b-2c526f7cafb2",
              "name": "test product",
              "price": 10.5,
              "createdAt": "2021-07-03T13:07:22.156Z"
          },
          {
              "id": "cec795c3-20b9-4b29-9146-e2a8071edcf3",
              "name": "product name",
              "price": 0.5,
              "createdAt": "2021-07-03T14:01:29.156Z"
          }
      ],
      "pageable": {
          "sort": {
              "sorted": false,
              "unsorted": true,
              "empty": true
          },
          "pageNumber": 0,
          "pageSize": 20,
          "offset": 0,
          "unpaged": false,
          "paged": true
      },
      "last": true,
      "totalPages": 1,
      "totalElements": 2,
      "sort": {
          "sorted": false,
          "unsorted": true,
          "empty": true
      },
      "first": true,
      "numberOfElements": 2,
      "size": 20,
      "number": 0,
      "empty": false
    }

### Create Order

#### Request

    POST /api/v1/orders
    Content-Type: application/json
    
    {
      "buyerEmail": "test@email.com",
      "products":[
          {
              "id": "9ff102e0-9d1a-4285-988b-2c526f7cafb2",
              "quantity": 1
          }
      ]
    }

#### Responses

    204 CREATED

    {
      "id": "da10a01b-4247-42d2-a78a-7a98f4aa0ef2",
      "buyerEmail": "test@email.com",
      "products": [
          {
              "id": "9ff102e0-9d1a-4285-988b-2c526f7cafb2",
              "name": "test product",
              "quantity": 1,
              "price": 10.5
          }
      ],
      "createdAt": "2021-07-03T13:07:45Z",
      "totalPrice": 10.5
    }

---

    400 Bad Request
    When input is not readable, email is not valid, products array is empty or quantity is less than 1

---

    404 Not Found
    When given product not found.

### Get Orders

#### Request

    GET /api/v1/orders?startDate={startDate}&endDate={endDate}&page={page}&size={size}&sort={sort}
    
    startDate: UTC date e.g. 2021-07-03T12:04:55Z
    endDate: UTC date e.g. 2021-07-04T12:04:55Z
    page: Zero-based page index (0..N). Default 0
    size: The size of the page to be returned. Default 20
    sort: Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported. e.g. createdAt,DESC

#### Responses

    200 OK

    {
      "content": [
          {
              "id": "da10a01b-4247-42d2-a78a-7a98f4aa0ef2",
              "buyerEmail": "test@email.com",
              "products": [
                  {
                      "id": "9ff102e0-9d1a-4285-988b-2c526f7cafb2",
                      "name": "test product",
                      "quantity": 1,
                      "price": 10.5
                  }
              ],
              "createdAt": "2021-07-03T13:07:45Z",
              "totalPrice": 10.5
          }
      ],
      "pageable": {
          "sort": {
              "sorted": false,
              "unsorted": true,
              "empty": true
          },
          "pageNumber": 0,
          "pageSize": 20,
          "offset": 0,
          "unpaged": false,
          "paged": true
      },
      "last": true,
      "totalPages": 1,
      "totalElements": 1,
      "sort": {
          "sorted": false,
          "unsorted": true,
          "empty": true
      },
      "first": true,
      "numberOfElements": 1,
      "size": 20,
      "number": 0,
      "empty": false
    }

Swagger-UI
---
Swagger-UI is available in the project. You can access it via the following URL:

    http://localhost:8080/swagger-ui.html

OpenAPI 3.0 Spec
---

You can find the OpenAPI 3.0 spec under api folder.

## Answers

- We can introduce OAuth2 security (with JWT tokens) by adding just a few libraries and configuration classes.
- Not sure what is meant by `redundant` but we can split this service into two microservices, ProductService and
  OrderService. Splitting won't be too hard since domains are splitted.