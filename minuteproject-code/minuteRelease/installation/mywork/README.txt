Edit your configuration:
------------------------

In config:
Take a copy of mp-config-sample.xml to <my-model-config.xml>
In this configuration there are 2 parts:
One is related to the model, its properties and enrichment.
The other is related to the target technologies. Uncomment the part(s) to use one (or more) target.

Execution:
----------

Under windows run:
model-generation.cmd <my-model-config.xml>

Under unix, linux run:
./model-generation.sh <my-model-config.xml>
