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
#### Pivotal 
Tabular results may contain similar information between rows for the same column

| film_id | film_name | actor_id | actor_name |
|---------|-----------|----------|------------|
| 1       | film_A    | 1        | actor_A    |
| 1       | film_A    | 2        | actor_B    |

For those 2 rows film_id and film_name are redundant in the result set.
Pivotal pattern applied on film groups film info (film_id, film_name) and associate with the collection of actors info (actor_id, actor_name)

It is a way to return tabular information into a graph.

### Field Column
#### Stereotype
Stereotype are combination of validation and presentation info
#### check constraint
A field can contain only some values among a set. 
A check constraint field is a candidate for master data hardcoded value, since a change in the constraint is a change of the model/business that requires another generation.
#### Graph
By using a cell (row, column) content as a structured data it can store a single or a collection of objects.
This can constitute a graph.
