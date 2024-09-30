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

