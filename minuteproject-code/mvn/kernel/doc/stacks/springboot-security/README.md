# Springboot Security
## Authentication
TODO
## Authorization
### Global security
Security Role based for a user within the Application.
To achieve it, create 2 queries:
* One that retrieves all the global roles in format of enum. This query is tagged with 'is-global-role' to true, 'is-enum' to true
* One that retrieve all the global roles a connected user has. Thise query is tagged with 'is-global-user-role' to true
#### Global role query example
It must contains only one column
```xml
<query name="global_role" id="global_role"
       package-name="metadata"
       content-type="master-data"
       is-enum="true"
       is-global-role="true"
>
    <query-body>
        <value>
            <![CDATA[
select role as global_role from t_role
]]>
        </value>
    </query-body>
</query>
```
#### Global iser role query example
It must contains only one column
```xml
<query name="global_role" id="global_role"
       package-name="metadata"
       content-type="master-data"
       is-enum="true"
       is-global-role="true"
>
    <query-body>
        <value>
            <![CDATA[
select role from user_role_view where user_login = ?
]]>
        </value>
        <query-params>
            <query-param name="user_login" type="string" sample="'test'"/>
        </query-params>
    </query-body>
</query>
```
## Springboot with tomcat
To a