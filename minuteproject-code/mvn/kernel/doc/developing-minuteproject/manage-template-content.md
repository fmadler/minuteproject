# Manage template content

Templates are written in velocity

## Common libraries
Common libraries are used to share data between templates such as
* Template definitions for
    * class
    * import
    * variable

## Writing your template
A template has its metadata provided inside its template xml definition node (cf [manage-template-definition.md](manage-template-definition.md))

Regarding to its type you need to reference some global Velocity makro call.

Ex: for template of type column
```text
#exposeColumnSpecific()
```
So that $className and $packageName can be retrieved

### Mapping

| Template type | Load definition macro |
|---------------|-------------------------|
| query         | #exposeQuerySpecific()  |
| model         | #exposeVariableModelSpecific()  |
| entity        | #exposeVariableEntitySpecific()  |

## Templating tutorial

[TutorialEntityTemplate.vm](../../src/main/templates/tutorial/TutorialEntityTemplate.vm)

[TutorialFieldTemplate.vm](../../src/main/templates/tutorial/TutorialFieldTemplate.vm)

[TutorialModelTemplate.vm](../../src/main/templates/tutorial/TutorialModelTemplate.vm)
