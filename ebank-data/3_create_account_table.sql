create table ebank.account
(
id int NOT NULL AUTO_INCREMENT,
type varchar(50) NOT NULL,
balance DECIMAL(10,2) NOT NULL,
active boolean NOT NULL,
customer_id int NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (customer_id)
REFERENCES customer(id)
);