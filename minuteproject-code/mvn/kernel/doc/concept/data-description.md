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

### Caching
It is worth applying pagination on master-data, reference-dataf
