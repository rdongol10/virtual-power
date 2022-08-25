# Virtual power system

The project allows you to add the battery information and list out the batteries according to the postcode


## Technologies used
* Spring boot 2.7.2
* Java 11
* Maven

## Running application locally

You can either run the application on you local machine by executing the main method ind the class com.rdongol.virtualpowe.VirtualPowerApplication from your IDE.

Or navigate to the project folder from command line and run following command:

* mvnw srping-boot:run

Once you run the project the api is available at url "http://localhost:8080". And you can find the swagger documentation of the api at the url  http://localhost:8080/swagger-ui/.

## Hosted server

The application is also hosted in heroku. Following is the swagger link to the api documentation.

* https://virtual-power.herokuapp.com/swagger-ui/


## Postman collection

The postman collection json file "virtual-power.postman_collection.json" is committed in the project. You can import the json file in postman and start testing the api.

The postman file calls the api hosted in Heroku, so you do not have to run the project to test the api from postman.


## APIs

### Add batteries

URL for local environment - http://localhost:8080/battery/ 

URL for heroku - https://virtual-power.herokuapp.com/battery

Type="POSt"

Body:

[
{
"name":"batteryName",
"postCode":"postcode",
"wattCapacity":wattCapacity
}]


The api allows to save batteries. It takes list of batteries in body.


### List batteries

URL for local environment - http://localhost:8080/battery/findByPostCodes

URL for heroku - https://virtual-power.herokuapp.com/battery/findByPostCodes

Type="POSt"

Body:

{
"postCodes": ["postcode1","postCode2",....]
}

The api takes list postcode and returns the list of batteries associated with the postcodes.

















