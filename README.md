##BACKGROUND##
This project is for COMP 439: Distributed Systems at Loyola.  It is a simple REST service representing an online bank api.

There are two main objects in this system: customer and account.

The API provides basic CRUD functionality via REST for interacting with these objects.

Note: The delete functionality is a soft delete (inactivate account) as this is more appropriate for a bank.

This project is written in:
-Java
-Spring Boot
-Spring Web (for REST JAX-RS functionality)
-Lombok (for making code more readable)
-Mysql (hosted locally)

This project is utilizing a local mysql instance due to latency and performance issues.

##LOCAL MYSQL DB##

A local mysql database is needed in order to run this project.

Setup mysql with a root user with no password.  Note: Version this code base was tested with was 5.7.14.

Once MySQL is installed, run the following:

mysql.server start

mysql -u root -p

Note: No password needed

Run all db scripts under ebank-data to create database and associated tables. 

##RUNNING PROJECT##
To run project, execute the following command at the project root.  This will run the application in Spring Boot and spin up the endpoints.

gradle clean build && java -jar build/libs/ebank-0.0.1-SNAPSHOT.jar

##TESTING PROJECT##
To test project, I created an endpoint that will run through various tests using RestTemplate:
POST http://localhost:8080/test/loadtest

Note: Navigate back to the terminal to see the results.