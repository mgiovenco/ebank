To run project:
gradle clean build && java -jar build/libs/ebank-0.0.1-SNAPSHOT.jar

To test project:
POST http://localhost:8080/test

To connect to database (hosted on AWS):
mysql -h ebank.c5qycvuwlvdp.us-east-1.rds.amazonaws.com -P 3306 -u mgiovenco -p mgiovenco
use ebank;
show tables;