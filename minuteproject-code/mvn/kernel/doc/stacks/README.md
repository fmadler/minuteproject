#Stacks

## JAVA REST-GRAPHQL
### Techno
* Java 8+
* target server tomcat/weblogic
* maven build to release a war
* create REST & graphql schema on top of SDD (sql statements)

### release
Deployed war endpoints

#### REST
ex : http://localhost:8080/<deployedApp>/data/sdd/<StatementInput>

swagger :
http://localhost:8080/<deployedApp>/data/swagger.json

It depends on the swagger version; could also be /data/openapi.json

#### GraphQL 

GraphiQL test
ex: http://localhost:8080/<deployedApp>/data/graphql/graphiql

## SpringBoot
### Techno
* Java
* create REST endpoint for GET and POST methods

### release
Deployed as war or as spring-boot:run

To use embedded connection pool for local test
```bash
mvn clean package spring-boot:run -Dspring-boot.run.profiles=jdbc-params -Pjdbc-params
```
* Maven build profile for
    * including the jdbc driver
* Springboot runtime profile jdbc-params for
    * using jdbc connection params
    * local logback storage
    
Then go to http://localhost:8081/swagger-ui/index.html
