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
TODO 
```
 mvn minuteproject:generate -Dconfig=<config>
```
