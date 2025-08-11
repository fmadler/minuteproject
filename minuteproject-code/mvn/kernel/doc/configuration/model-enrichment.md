# Model enrichment

## Conventions
Conventions are a set of actions to apply to all the entities matching a specific pattern.

Conventions can benefit from previous conventions definitions.
Example:  One convention tags certain tables with a content-type, another convention use the content-type to perform further enrichment.

### Structure convention
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
### Content enrichment
#### searchable //TODO

#### entity content type convention

```xml
    <entity-content-type-convention type="apply-content-type-to-entity-belonging-to-package" pattern="type,config" content-type="reference-data"/>
    <entity-content-type-convention type="apply-content-type-to-entity-belonging-to-package" pattern="core" content-type="pseudo-static-data"/>
```

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

#### entity-searchable-convention convention
Indicate that columns are searchable and how to search on those column
```xml
	<entity-searchable-convention type="apply-searchable-equal-on-column" field-pattern-type="endsWith" field-pattern="web_path"/>
```
