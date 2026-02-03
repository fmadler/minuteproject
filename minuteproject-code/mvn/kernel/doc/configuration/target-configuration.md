# Target Configuration

Properties to set within the targets node are available to all the template to customize generation based on those values.

## Properties setup
Properties can be given at 3 levels:
* template properties from
  * template itself
  * target configuration
* template-target
* stack (in technology-catalog)
When evaluating a property the value given it the first retrieved by the order described above.

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
