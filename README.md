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
-Mysql (hosted on AWS)

The AWS instance is a free tier database and, as a result, not the best hardware or network capacity.

Note: For later assignments, I will upgrade this to have improved performance (when performance matters).  This is just to save $$$.

##RUNNING PROJECT##
To run project, execute the following command at the project root.  This will run the application in Spring Boot and spin up the endpoints.

gradle clean build && java -jar build/libs/ebank-0.0.1-SNAPSHOT.jar

##TESTING PROJECT##
To test project, I created an endpoint that will run through various tests using RestTemplate:
POST http://localhost:8080/test

Note: Navigate back to the terminal to see the results.

##CONNECTING TO DB##
For this project, since I'm hosting on the database on AWS, you don't need to create any local database.

If you want to check the database directly, you can connect to it with the following commands:
mysql -h ebank.c5qycvuwlvdp.us-east-1.rds.amazonaws.com -P 3306 -u mgiovenco -p mgiovenco
use ebank;
show tables;