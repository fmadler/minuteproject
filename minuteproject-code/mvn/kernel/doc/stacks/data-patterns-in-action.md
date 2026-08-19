# Data Patterns in Action
## Overview-List-Details
Pattern to guide the user from an overview or a dashboard downwards to the list of element and then to details of a select entity.

3-query-pattern:
* Overview query
* List query
* Detail query or Detail composite (multiple query)

It can be apply globally or user-centric (Connected user passed implicitly as parameter).
On Detail query/composite when use with a connected user there can be an accessibility check. In event of rejection, appropriate logging and exception can be thrown.

### Example
A user wants to see the number of dossiers he has access by type and status (2 dimensions).
Then based on a type and status he wants to see the list of dossier with pagination.
Eventually he checks the details of a dossier with one or multiple queries.

## Cascade Drop-down list
Use to drill down info

## Recursive

## Entity Visibility - User centric
User centric means that a user is already authenticated by this application and that it's userId (login) can be retrieved.
### Filtering in 
At SQL level:
One query joining multiple subqueries passing or not the connected user has filter.
So in one query the result are columns with and without data (not visible).

If no access => exception (no data returned: either entity does exist (so checking something not present) or user does not have access)
If access => fill data according to visibility

### Prefilter
A first query is issue for accessibility (it can be reused) instead of embedded it in the sql.

If no access => exception