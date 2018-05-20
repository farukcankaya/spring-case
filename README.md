# spring-case
[![CircleCI](https://circleci.com/gh/farukcankaya/spring-case.png?style=shield&circle-token=a54438e7596cfd02ea92da8d8d6ba5d3408b2279)](https://circleci.com/gh/farukcankaya/spring-case)  [![Codacy Badge](https://api.codacy.com/project/badge/Grade/500b501963c6434480d07be10b6ebaab)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=farukcankaya/spring-case&amp;utm_campaign=Badge_Grade)  [![Codacy Badge](https://api.codacy.com/project/badge/Coverage/500b501963c6434480d07be10b6ebaab)](https://www.codacy.com?utm_source=github.com&utm_medium=referral&utm_content=farukcankaya/spring-case&utm_campaign=Badge_Coverage)

## TODO
- [x] **Expectations**
  - [x] Campaign CRUD create/update/delete
  - [x] Discount calculation
  - [x] Swagger integration (!)
- [ ] **Bonus**
  - [ ] Admin panel to manage campaign
  - [x] Dockerize application


## Running
You have two options to run application:
- **Manuel**
  Command below will build the application and generates runnable jar file and if you have **java** on your machine application will be operable on **localhost:8090**:
  
  `./gradlew build && java -jar build/libs/spring-case-0.0.1-SNAPSHOT.jar`
- **Docker**
  If you have docker you can run application on your docker containers.
  
  - `docker-compose build` : build docker image
  - `docker-compose up` : Run application on generated docker containers
  
  You can see running containers with `docker-compose ps` command. Because of using docker compose, applications are running on different port, you also can learn which ports the application running on with that command.
  
## Testing
There is automated test with <a href="https://circleci.com/">CircleCI</a> and codecoverage is measuring by <a href="https://www.codacy.com/">Codacy</a> but you can test it on your machine with following commands:

- `./gradlew test` : Run tests
- `./gradlew jacocoTestReport` : Generages test coverage reports with <a href="https://www.eclemma.org/jacoco/">Jacoco</a>
- `./gradlew codacyUpload` : This command uploads test results to Codacy but you need to give token as said here: https://github.com/ddimtirov/codacy-gradle-plugin

If you want to work with H2 database in you local machine you need to change test/application.properties file like this:
```yml
spring.datasource.url = jdbc:h2:mem:test
+spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect
```
  
## Configuration
  - Google formatter will be used in this project: https://github.com/google/google-java-format
 
