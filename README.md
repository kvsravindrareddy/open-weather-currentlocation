# open-weather-currentlocation
Open Weather API application with Spring Boot

Step 1 : Clone the Application

Step 2 : Build the application using maven build tool
>mvn clean install -DskipTests=true

Step 3 : Run the Jar file which generated in target folder. (Since this is the Spring Boot application it runs on the Embed tomcat server)
>java -jar open-weather-currentlocation.jar

Step 4 : Open the application swagger ui with the link below.
>http://localhost:8090/swagger-ui.html

Below Steps additional details. No action is required
Step 5 : PostgreSQL Database used for this application is Amazon WebService Cloud (URL, UserName, Password changes is not required for this application)

Step 6 : Weather API URL and API Subscription is specified in application.properties file

Step 7 : Since I am not the expertise in the Angular JS, created one rest controller and controller which use the MVC pattern with Thyleaf template engine.
CurrentWeatherController.java (This is the MVC controller)

Step 8 : CurrentWeatherRestController.java (This is rest controller contains all the Rest End points.

Step 9 : Junit is completed but failing few test cases. ( 60.1% Code Covered)

Step 10 : Implemented Swagger API documenation for API's

Step 11 : Application loggers are added

Step 12 : Exceptional scenarios are covered.

Step 13 : This application can be deployed in any external server by changing the <packaging> tag from jar to war in pom.xml file.
