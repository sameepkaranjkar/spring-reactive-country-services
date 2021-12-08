# reactive-countryservices
# Country Service API using Spring Boot 

Rest API to receive information about contry


# External Service used
o https://restcountries.eu/  API

# Technologies
o Java
o Reactive Spring Boot
o REST & JSON 

# Note :

o API Keys needs to be passed from request header

## How to run the Application

1. #### Clone

`git clone https://github.com/sameepkaranjkar/spring-reactive-country-services.git`

2. #### Mvn clean package
`./mvn -DskipTests clean package`
 
3. #### Run
`./mvn spring-boot:run`


4. #### Example :Get information of the country with given name
   ### Pass access_key and value as header information   
   http://localhost:8080/countries/Finland

5. #### Example :Get information of all countries
   ### Pass access_key and value as header information 
   http://localhost:8080/countries

6. Simple web application which utilizes the created REST API and shows the relevant     country information in a browser.
    http://localhost:8080/index.html

7. Open api documentation and Swagger ui :
   http://localhost:8080/swagger-ui/index.html#/country-application-controller
   http://localhost:8080/country-api-docs
  

	

 



  


