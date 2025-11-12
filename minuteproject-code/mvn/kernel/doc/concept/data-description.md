# Data Description

## Data Content

| Data content type | Description |
|-------------------|-------------|
| master-data       | Data that are bound to the application (ie any new data is a new release <br/> Ex workflow status; enumerations corresponding to check constraints on column with enumeration in the DB |
| reference-data    | Data that on admin or super admin can manipulate <br/> usually table constaining types|
| pseudo-static-data | Data that a end-user can manipulate but are few and do not change frequently (ex: bank account) |
| live-business-data | Data that are numerous and frequently added/manipulated (ex: a tweet, a draft) |


## Data Pattern
### Pagination
It is worth applying pagination on live-business-data
#### Track
* SpringBoot on entities marked as live-business-data provides springdata jpa pagination

### Caching
It is worth applying pagination on master-data, reference-dataf

## Data query result
### Tabular
### Graph
By using a cell (row, column) content as a structured data it can store a single or a collection of objects.
This can constitute a graph.

### Field Column
#### Stereotype
Stereotype are combination of validation and presentation info
#### check constraint
A field can contain only some values among a set. 
A check constraint field is a candidate for master data hardcoded value, since a change in the constraint is a change of the model/business that requires another generation.

