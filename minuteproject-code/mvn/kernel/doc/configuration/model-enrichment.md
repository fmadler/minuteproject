# Model enrichment

## Model Scoping
Generation can be scope to some DB objects such as tables or views.

### Disabling entity generation
At generator-config level
```xml
    <configuration>
        <conventions>
            <target-convention type="disable-business-model-generation" />
        </conventions>
    <configuration>
```
### Entity Scoping
At model/business level
```xml
    <business-model>
        <!-- exclude the generation for tables artifacts-->
        <generation-condition exclude-tables="true">
            <!-- exclude the generation for entity starting with v_sitemap -->
            <condition type="exclude" startsWith="v_sitemap" />
        </generation-condition>
```
## Model Factoring
Provide naming and packaging convention to entities and fields
### Package Factoring
Entities (tables and views) can be grouped within some packages
```xml
    <business-model>
        <!-- add default package -->
        <business-package default="view">
            <condition type="package" endsWith="_TYPE" result="type"></condition>
            <condition type="package" startsWith="sales_" result="config"></condition>
            <condition type="package" startsWith="V_" result="view"></condition>
            <condition type="package" endsWith="_V" result="view"></condition>
        </business-package>
    </business-model>
```

### Entity Factoring
It can be perform on individual basis or globally by [Structural Conventions](#structure-convention)

## Entity
### Semantic reference
```xml
    <entity name="CONFERENCE_MEMBER">
        <semantic-reference>
            <sql-path path="FIRST_NAME" />
            <sql-path path="LAST_NAME" />
        </semantic-reference>
    </entity>
```
## Fields
### Check constraints
```xml
    <entity name="CONFERENCE_MEMBER">
        <field name="STATUS">
            <property tag="checkconstraint" alias="conference_member_status">
                <property name="PENDING" value="PENDING" />
                <property name="ACTIVE" value="ACTIVE" />
                <property name="DONE" value="DONE" />
            </property>
        </field>
    </entity>
```
### Stereotype
Stereotype are combination of validation and presentation info
```xml
    <entity name="CONFERENCE_MEMBER">
        <field name="EMAIL">
            <stereotype stereotype="EMAIL" />
        </field>
    </entity>
```
## Conventions
Conventions are a set of actions to apply to all the entities matching a specific pattern.

Conventions can benefit from previous conventions definitions.
Example:  One convention tags certain tables with a content-type, another convention use the content-type to perform further enrichment.

### <a name="structure-convention"></a> Structure convention
#### entity-naming-convention
Allows to have generated artifacts different from the original DB entity name.
DB conventions can be distinct from target code (ex: java) convention
Ex:
```xml
    <entity-naming-convention type="apply-strip-table-name-prefix"
                              pattern-to-strip="GS_,V_" />
    <entity-naming-convention type="apply-strip-table-name-suffix"
                              pattern-to-strip="_V" />
```
#### column-naming-convention
Allows to have generated artifacts different from the original DB column name.
DB conventions can be distinct from target code (ex: java) convention
Ex:
```xml
    <column-naming-convention type="apply-strip-column-name-prefix"
                              pattern-to-strip="GS_" />
    <column-naming-convention type="apply-strip-column-name-suffix"
                              pattern-to-strip="_ID" />
```
#### reference naming convention

```xml
    <reference-naming-convention
            type="apply-referenced-alias-when-no-ambiguity" is-to-plurialize="true" />
    <reference-naming-convention type="apply-many-to-many-aliasing"
                                 is-to-plurialize="true" />
```

#### primary key convention

```xml
    <primary-key-convention type="apply-primary-key-on-entity-with-two-columns-only-and-foreign-key-otherwise-specified" />
    <primary-key-convention type="apply-default-primary-key-otherwise-first-one" />
```
#### view primary key convention
View do not have a primary key, meanwhile if there is a column that is unique within the view, it can play the role of a primary key.
The technologies requiring some identity column can then operate on top of views.
> Tip: sometime the concatenation of couple of column of the view the a separator can create this unique column
```xml
    <view-primary-key-convention
            type="apply-default-primary-key-otherwise-first-one"
            default-primary-key-names="IDENTIFIER,ID" />
```
#### foreign key convention
If some entities do not have fk by follow some naming conventions, those links (foreign keys) can be detected.
> Tip: very useful when working with a set of views where you can apply relationship and map them in an ORM.
> It creates virtual entities and an alternative graph that you DB table model.
```xml
	<foreign-key-convention 
        type="autodetect-foreign-key-based-on-similarity-and-map"
        column-ending="_id" />
    <foreign-key-convention 
        type="autodetect-foreign-key-based-on-similarity-and-map"
        column-ending="id" />
    <foreign-key-convention 
        type="autodetect-self-reference-foreign-key-based-on-column-name"
        column-ending="parent_id" />
```

### Content enrichment
#### Searchable entity-searchable-convention convention
Indicate that columns are searchable and how to search on those column
```xml
	<entity-searchable-convention type="apply-searchable-equal-on-column" field-pattern-type="endsWith" field-pattern="web_path"/>
```

#### entity content type convention

```xml
    <entity-content-type-convention 
        type="apply-content-type-to-entity-belonging-to-package" 
        pattern="type,config" 
        content-type="reference-data"/>
    <entity-content-type-convention
        type="apply-content-type-to-entity-belonging-to-package" 
        pattern="core" 
        content-type="pseudo-static-data"/>
    <entity-content-type-convention
            type="apply-content-type-to-entity-starting-with" 
            pattern="V_"
            content-type="live-business-data" />
    <entity-content-type-convention
            type="apply-content-type-to-entity-containing"
            pattern="view"
            content-type="live-business-data" />
```
3 types are available
* apply-content-type-to-entity-belonging-to-package
* apply-content-type-to-entity-starting-with
* apply-content-type-to-entity-containing

The content-type can have 4 values:
* master-data
* reference-data
* pseudo-static-data
* live-business-data

Specifying some type can lead to specific generation described [here](../concept/data-description.md)

#### semantic-reference-convention
```xml
    <semantic-reference-convention content-type="reference-data"
                                   field-pattern="name,number,amount" field-pattern-type="endsWith"
                                   force-default-semantic-reference-based-on-id-and-first-attribute-if-not-present="true"
                                   max-number-of-fields="5"
    />
    <semantic-reference-convention content-type="pseudo-static-data"
                                   field-pattern="name,web_path" field-pattern-type="endsWith"
                                   force-default-semantic-reference-based-on-id-and-first-attribute-if-not-present="true"
                                   max-number-of-fields="5"
    />
```

#### ordering convention
```xml
    <ordering-convention field-pattern="DISPLAY_ORDER"
                         field-pattern-type="endsWith" ordering="asc" />
```

## Query result enrichment
### Linking result
A query can be referenced by another query result via a query-param
```xml
    <query-params>
        <query-param name="categories" type="string" is-in-clause="true" sample="'hx','tx'">
            <query-param-link sdd-query-name="distinct-categories" field-name="name" field-key="category"/>
        </query-param>
    </query-params>
```
This query has a query param called category that has its input restricted to the result of the referenced
query-name : 'distinct-categories'. <br/>
For display purpose the field to see comes from field-name, and the field to query or store comes from field-key. 
field-name and field-key may be the same. 

### Cell graph enrichment
A cell (row, column) of a sql result can contain some structured data that can be parsed into object (single or multiple).

**Example** The field winners contains a collection of object (name: string, web_path:web_path)
```xml
    <query-field name="winners"
                 is-structured-array="true"
                 separator-characters=",|"
                 array-columns="name,web_path"
                 array-columns-type="string,string">
    </query-field>
```
#### When to use
When you have a collection of collections such as a query returning a list of users and each user has a list of email addresses.

Instead of going for an N+1 pitfall design: 
> List of chosen user (1 query) + for those N users get for each the list of email addresses (N queries)

Gather all those info into one query by using some sql aggregation functionalities (such as GROUP_CONCAT for mysql).
On column will have this information that can be used by minuteproject enrichment to generate proper objects.

```sql
    select a, b, 
        GROUP_CONCAT(distinct
                     CONCAT_WS(
                             '|',
                             pl.name,
                             pl.web_path
                         )
            ) winners
    from player pl ...
    group by a, b
```
