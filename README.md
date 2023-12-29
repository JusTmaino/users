# Users Application

This Application is about users management.

## Architecture
- The Architecture used is based on clean Architecture

- Separation between different layers such web, domain, service, entity ...etc to have low dependencies between the business (domain) layer, Access (web) layer and persistence (Entity) layer.

## User model

The user model have 6 attributes:
- id 
- firstname: mandatory
- lastname: mandatory
- age: mandatory
- address: optional
- country: mandatory

## REST calls
There is two rest endpoints exposed:

- POST /user: 

    - Create user with conditions, only adults ( age > 18 years) and that live in France can create an account, otherwise it return a forbidden error (403)
    - As the address is optional, a default value "Place du gaulle 06600 Antibes" is being set in case it is not mentioned in the request


- GET /user/{id}: 

    - get a specific user by id (id is a path variable)
    - if the user with the given id doesn't exist in the database, a resource not found exception will be thrown (404)


## Swagger V3 (Open api)

The swagger is accessible via this link: http://localhost:8080/swagger-ui/index.html

## Postman collection

Postman collection is provided under postman folder
