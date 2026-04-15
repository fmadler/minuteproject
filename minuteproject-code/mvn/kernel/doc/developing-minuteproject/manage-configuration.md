# Manage configuration
Make the configuration and the model evolve

## Manage Model configuration
### xsd adaptation
Check [mp-config.xsd](../../src/main/config/mp-config.xsd)
### xml mapping 
objects are load via betwixt (to migrate) into objects
#### betwixt adaptation
Add mapping field and objects
Check [model-config-rules.xml](../../src/main/resources/net/sf/minuteProject/configuration/model-config-rules.xml)

> 2 parts need to be adapted
> * Model part
> * Application/Model part
> 
> So that generation based on model or application can use the same structure.
> 
> Search for the object
> then add the field-object mapping

#### java mapping object adaptation
Add mapping field and objects

## Populating object
Now your object/field can be used in your model configuration


## Complement model information
Once the configuration loaded, extra processing to complement the model might be required

This is the case for query that are executed and whose input and output structure result is mapped into tables.

## Designing conventions

## Manage Templage configuration
### xsd adaptation
Check [mp-template-config.xsd](../../src/main/config/mp-template-config.xsd)



