# Configuration

## Global
* disable-timestamp-comment-marker 

To not add a timestamp inside the generated artifact (useful is code is flushed on git so that it does not appears as new code in nothing safe the timestamp has changed)

* disable-business-model-generation 

If you do not want to generate artifacts from DB tables or views.
Useful when you generate from statements only.

Example
```
    <configuration>
   		<conventions>
			<target-convention type="disable-timestamp-comment-marker" />
			<target-convention type="disable-business-model-generation" />
```
## Model
Define a primary key strategy (SEQUENCE (sequencePattern), AUTOINCREMENT (autoincrementPattern),UUID (uuidPattern))

Example for sequence 
```
 <primaryKeyPolicyPattern name="sequencePattern" suffix="_SEQ" ></primaryKeyPolicyPattern>
```
### Generation condition
Generation of model artifacts is by default for tables and views.
Generation of model can be restricted to tables or views.

Example for no artifact generation for views
```
 <generation-condition exclude-views="true" default-type="exclude">
```

### Model enrichment
A model can be enriched:
* @entity level
* @global level via [model-enrichment-conventions](model-enrichment.md)
