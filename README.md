# Read Me First
The following was discovered as part of building this project:

A RESTful API for managing dogs, suppliers, and their associated details.  
Built with **Spring Boot**, **Flyway**, **JPA**, and **H2** (for development).

# How To Run
* > ./mvnw clean install
* > ./mvnw spring-boot:run

## H2 database client:
* > credentials in application.properties: http://localhost:8080/h2-console

## HTTP endpoints calls: 
* > src/test/dogs-api.http

## Features

- CRUD operations for dogs and suppliers
- Soft deletion support
- Filtering and pagination using JPA Specifications
- Automatic database migrations via Flyway
- In-memory H2 database for local testing
- DTO-based mapping with MapStruct
- Enum-based domain modeling (Gender, CurrentStatus, etc.)

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.6/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.5.6/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.5.6/reference/web/servlet.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.5.6/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Validation](https://docs.spring.io/spring-boot/3.5.6/reference/io/validation.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

