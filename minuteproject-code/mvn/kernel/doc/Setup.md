# Setup

## build
```bash
mvn clean install 
```

## Generate
### ModelViewGenerator
If you have a model (tables, views, stored procedures) use ModelViewGenerator main class. The format of the xml file must be composed of a model only
```
mvn exec:java -Dexec.mainClass="net.sf.minuteProject.application.ModelViewGenerator" -Dexec.args="HD-LEOS.xml"
```
### ApplicationGenerator
If you have multiple models (tables, views, stored procedures) use ApplicationGenerator main class. The format of the xml file must be composed of a application that wraps multiple models
```
mvn exec:java -Dexec.mainClass="net.sf.minuteProject.application.ModelViewGenerator" -Dexec.args="HD-LEOS.xml"
```
