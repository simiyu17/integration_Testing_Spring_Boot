# INTEGRATION TEST IN SPRING BOOT API

This is a sample unit test in Spring Boot CRUD API application. 

## Update
- Make necessary datasource updates in src/main/resources/application.properties

## Deploy Project
- Change directory to project folder and  run `mvn clean install` to run tests.
- If you run the application, the api url is `http://127.0.0.1:8083/api/v1/students` 

  - Sample post/put json payload
  `{
  "firstName": "Jane",
  "lastName": "Simiyu",
  "gender": "MALE",
  "dateOfBirth": "2000-07-17"
  }`

## Used Tech
- Spring Boot 2.3.0
- Maven
- Mockito
- Mysql
- JUnit 5


### From Developers

I am always happy to receive your feedback!
FInd me on [Twitter](https://twitter.com/julian_geniuz)!
