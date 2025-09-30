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

## REST-BSLA

### Build
```bash
mvn clean package
```

If you have the property target-server set to tomcat
```bash
mvn clean package -P tomcat-embedded-ds
```

### Deploy on tomcat 
Copy REST/target/<app>.war to your tomcat /webapps directory
Then go to http://localhost:8080/data/graphql/graphiql


## SpringBoot
### Techno
* Java
* create REST endpoint for GET and POST methods

### release
Deployed as war or as spring-boot:run

To use embedded connection pool for local test

Inside /REST directory
```bash
mvn clean package spring-boot:run -Dspring-boot.run.profiles=jdbc-params -Plocal
# or
mvn clean package spring-boot:run -DactiveProfiles=jdbc-params -Plocal
```
* Maven build profile for
    * including the jdbc driver
* Springboot runtime profile jdbc-params for
    * using jdbc connection params
    * local logback storage
    
Then go to http://localhost:8081/swagger-ui/index.html

If graphql is enabled, test with GraphiQL at
http://localhost:8080/<deployedApp>/graphiql

## Release run

```bash
mvn clean package -DactiveProfiles=jdbc-cp -Premote
```
