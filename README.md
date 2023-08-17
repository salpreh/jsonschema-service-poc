# json-schema-service-poc
SpringBoot service to validate JSON against a JSON Schema in a tiny business domain model.

## Overview
The domain model is very simple. There is a `ContractType` and a `Contract` entity.
A `Contract` is associated to a `ContractType` and has a `json` field that contains the contract data. The `ContractType` has a `jsonSchema` field that contains the JSON Schema that the `Contract` must validate against.

There is a CRUD controller for each entity and a collection of requests in `http-requests/requests.http` to test.

## How to run
1. Init the database with docker-compose
```
docker-compose up -d
```

2. Build the project
```
mvn clean package
```

3. Run the application
```
mvn spring-boot:run
```