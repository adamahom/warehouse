## Warehouse Application
___
### Description
This application has two functionalities: get the list of products with quantity and buy a product.
### Requirements
To run the application, you need to have java 8 installed on your machine. Then, you can run the application with the following command:
#### Mac:
```$brew cask install java```
#### Windows:
```$choco install jdk8```

Also, you need to provide a postgres database which you have to configure it in: 
```src/main/resources/application.properties```
#### Example:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/warehouse
spring.datasource.username=postgres
spring.datasource.password=postgres 
```

### Run the application

After Installation of java 8 and configure application you can run the application with the following command:
#### Mac/Linux:
```$./mvnw spring-boot:run```
#### Windows:
```$mvnw.cmd spring-boot:run```

### Api Documentation
After running the application, you can access the api documentation from [here](http://localhost:8080/swagger-ui/index.html)

### Missing features
- [ ] Add Unit tests.
- [ ] Improve Swagger docs.
- [ ] Right now the application is not thread safe, need to add a lock to handle concurrent product buy.
- [ ] Add a docker compose file to run the application with a postgres database in a container.
- [ ] Add a CI/CD pipeline to run the tests and build the application.
- [ ] Add load tests to check the performance of the application.
- [ ] Add a monitoring tool to check the health of the application.
- [ ] Add a logging tool to log the application logs.



