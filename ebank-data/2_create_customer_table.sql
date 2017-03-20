create table ebank.customer
(
id int NOT NULL AUTO_INCREMENT,
first_name varchar(50) NOT NULL,
last_name varchar(50) NOT NULL,
phone varchar(50) NOT NULL,
active boolean,
PRIMARY KEY (id)
);