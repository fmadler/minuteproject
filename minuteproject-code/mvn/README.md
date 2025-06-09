# Minuteproject
## Configuration
Minuteproject loads a configuration that indicates the target technologies to apply.

## Model enrichment
* sdd-cache-convention
  * type:
      * apply-cache-to-entity-belonging-to-package
      * apply-cache-to-entity-not-belonging-to-package
      * apply-cache-to-content-type
      * apply-cache-to-entity-starting-with
      * apply-cache-to-entity-ending-with
  * pattern

```xml
<statement-model>
	<enrichment>
		<conventions>
			<sdd-cache-convention 
				type="apply-cache-to-content-type" 
				pattern="reference-data"/>
		</conventions>
	</enrichment>
</statement-model>
```

## Development
### Target technologies
The target technologies are defined in technology-catalog.xml

Each technology has:
* a name
* template-config-file-name that points to a mp-template-config-xxx.xml with xxx the name of the technology
* default-outputdir
* template-dir="@templateRoot@/framework/bsla"
* status="release"
* description
* depends-on-targets= can depend on other technology
* is-generable boolean

The generation for some technologies is defined in some mp-template-config-xxx.xml 

Each mp-template-config contains a set of template-targets that contain
* a name
* outputdir
* a list of template target properties
* a list of template

Each template have
* a name
* templateFileName
* subdir=""
* outputsubdir="src"
* technicalPackage="datacontrol"
* file-extension="java"
* file-prefix=""
* file-suffix="XX"
* model-specific="true"
* has-updatable-nature="true"
* add-model-dir-name="false"
* add-model-name="true"

  
