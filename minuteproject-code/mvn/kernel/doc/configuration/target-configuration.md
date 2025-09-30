# Target Configuration

Properties to set within the targets node are available to all the template to customize generation based on those values.

## Target server
target-server property allows you to add target server information
```xml
<property name="target-server" value="tomcat" />
```

## Graphql enabled
indicates to generate graphql artifacts if target stack contains graphql code
```xml
<property name="graphql-enabled" value="true" />
```
* tracks: SpringBoot, REST-GRAPHQL_API
