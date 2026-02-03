# Commands
## Build
```
mvn clean package 
```

## Sonar 
Check sonarqube server has started
* get a token for your project (<token>)
* Disable the SCM Sensor in admin>SCM 
```
 mvn sonar:sonar -Dsonar.login=<token>
```

## Generate  
```
 mvn minuteproject:generate -Dconfig=<config>
```
## Configuration
check [./doc/configuration/README.md](./doc/configuration/README.md)

### Application & models

### Targets properties

| Property | Value(s) | Default|Description |
|----------|----------|--------|------------|
|target-server| tomcat, weblogic | NA |used for Connection pool config on server |
|ilter-cors| apache | adds CORS config and add apache filter lib in the delivery or use the embedded tomcat one if target-server=tomcat |
|environment |remote, local | NA |used for Connection config |
|add-named-queries |true, false | false | used for annotating persistence model with named query in java |
|enable-velocity-rendition |true, false | false|used for annotating persistence model with named query in java |

 
