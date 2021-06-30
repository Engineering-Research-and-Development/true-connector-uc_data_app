# TRUE Connector Usage Control Data App

Derived from IDS Usage Control App by Fraunhofer IESE


[![License: AGPL](https://img.shields.io/github/license/Engineering-Research-and-Development/true-connector-fiware_data_app.svg)](https://opensource.org/licenses/AGPL-3.0)
[![Docker badge](https://img.shields.io/docker/pulls/rdlabengpa/ids_uc_data_app.svg)](https://hub.docker.com/r/rdlabengpa/ids_uc_data_app)
<br/>

The TRUE Connector Usage Control Data App, based on IDS Usage Control App by Fraunhofer IESE enabling:

- provides access to a resource with specific identifier defined by the target property or rule in a specific time interval

## How to Configure

The configuration provides information that the default values ​​are as follow. If someone wants to tweak default values - application.yml file is starting point.

- set the port 
	server:
     port: 9555
	 
- save the policies to the file storage(If you are using a persistance database to store the policies set it to false. (Default = true))
    application:
	 savePoliciesToFilestorage: true
	 
- add the path of your directory where you want to save the policies:
	odrlPolicyDirectory:

## Run the application and import rules

UsageControl dataApp exposes endpoint for importing rules. Following endpoint can be found at:
   
   http://localhost:9555/swagger-ui.html
   
  
On the following screenshots instructions for importing rule are depicted

Click on odrl-policy-controller, then select post addPolicy from the dropdown menu. 

![Importing rules](doc/importing_rules_1.png?raw=true "Importing rules")

The next step is to import rule, click on try it now and import your rule instead "string"(rules can be found in the part UsageControl examples). 

![Importing rules](doc/importing_rules_2.png?raw=true "Importing rules")

Finally click on execute, the status code in response will be 200 and rule will be imported succesfully.

![Importing rules](doc/importing_rules_3.png?raw=true "Importing rules")

## UsageControl examples

On the following links can be found usage control policy examples with description

## Time-Based Interval Rule

The following rule describes the time interval in which it is allowed to access the resource with a specific identifier defined using the “target” property of the rule.

The link below is located an example of time-based rule: leftOperand is the start date and rightOperand represents the end date, in the specific case the message can be consumed only from 15th June 2021 to 31st December 2021.

[Time-Based Interval Rule](https://github.com/Engineering-Research-and-Development/true-connector-uc_data_app/blob/master/src/main/resources/policy-examples/0.0.3/1%20restrict-access-interval.json)

## Modifier Rule

### Anonymize
The following rule describes an example of modifier rule, anonymize, which will modify the payload response using json path. The current limitation is that the payload must be json string in order to be able to apply rules with modifiers.

In the link below is located an example of modifier rule: replaceWith filed tells which string will be used to replace the current value (dateOfBirth will be replaced with xxxx).

[Anonymize field in transit Rule](https://github.com/Engineering-Research-and-Development/true-connector-uc_data_app/blob/master/src/main/resources/policy-examples/0.0.3/3%20anonymize-in-transit-replace.json)


Example of payload, on which policy will be applied:

```
{
   "firstName":"John",
   "lastName":"Doe",
   "address":"591  Franklin Street, Pennsylvania",
   "checksum":"ABC123 2020/11/03 11:56:25",
   "dateOfBirth":"2020/11/03 11:56:25"
}
```

Palyoad after policy is applied:

```
{
   "firstName":"John",
   "lastName":"Doe",
   "address":"591  Franklin Street, Pennsylvania",
   "checksum":"ABC123 2020/11/03 11:56:25",
   "dateOfBirth":"xxxx"
}
```

### Delete
The following rule describes another example of modifier rule, delete, which will modify the payload response, removing the specified entry from the payload (defined through the jsonPath property). The current limitation is that payload must be a json string in order to be able to apply rules based on modifiers.

In the link below is located a running example of modifier rule: the jsonPath field will be used to evaluate and remove following the specified object (dateOfBirth) from the payload.

[Delete field in transit Rule](https://github.com/Engineering-Research-and-Development/true-connector-uc_data_app/blob/master/src/main/resources/policy-examples/0.0.3/33%20anonymize-in-transit-delete.json)


Example of payload, on which policy will be applied:

```
{
   "firstName":"John",
   "lastName":"Doe",
   "address":"591  Franklin Street, Pennsylvania",
   "checksum":"ABC123 2020/11/03 11:56:25",
   "dateOfBirth":"2020/11/03 11:56:25"
}
```
Palyoad after policy is applied:

```
{
   "firstName":"John",
   "lastName":"Doe",
   "address":"591  Franklin Street, Pennsylvania",
   "checksum":"ABC123 2020/11/03 11:56:25"
}
```

## Spatial-Based Rule - Location

The following rule describes a location-based rule, which allows or inhibits the usage of resources with id defined in the target property based on connector location (country).

The link below is located an example of spatial-based rule: the rightOperand contains value IT, which tells that the resource can be consumed only by connectors that are located in Italy


[Spatial - Based Rule - Location](https://github.com/Engineering-Research-and-Development/true-connector-uc_data_app/blob/master/src/main/resources/policy-examples/0.0.3/4%20restrict-access-location.json)


## Purpose-Based Rule

The purpose-based class of rules allows or inhibits the usage of resources with an identifier defined in the target property based on message purpose.

In the link below is located an example of spatial-based rule: the rightOperand contains the value http://example.com/ids-purpose:Marketing, which tells that the resource is available for messages that have a purpose defined as Marketing.

[Purpose - Based Rule](https://github.com/Engineering-Research-and-Development/true-connector-uc_data_app/blob/master/src/main/resources/policy-examples/0.0.3/5%20restrict-access-purpose.json)


## Complex Rule

Rules can be composed in order to create complex permission definitions. The following rule describes a complex based rule, which contains 2 simple rules: spatial and time interval based. All those simple rules must be evaluated as true in order to allow the usage of the resource. If any of the simple rules is evaluated as false, then the resource usage is inhibited.

In the link below is located an example of the complex rule: spatial rule defines that the connector must be in Serbia and time base rule defines that the resource can be consumed only from 15th June 2021 until 31st December 2021.

[Complex Rule](https://github.com/Engineering-Research-and-Development/true-connector-uc_data_app/blob/master/src/main/resources/policy-examples/0.0.3/6%20restrict-access-complex-interval-location.json)
