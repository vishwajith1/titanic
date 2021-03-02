
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS sub_order;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS base_order;
DROP TABLE IF EXISTS customer;




CREATE TABLE  customer(
	id SERIAL PRIMARY KEY,
	name VARCHAR (50),
	extra_info VARCHAR (50),
	gender VARCHAR(10),
	age INTEGER
);
INSERT INTO customer(name,gender,age) VALUES ('Vishwa', 'male', 28);
INSERT INTO customer(name,gender,age) VALUES ('Krishna', 'male', 28);
select * from customer c ;


CREATE TABLE base_order (
	id SERIAL PRIMARY KEY,
	status VARCHAR (25),
	customer_id INTEGER,
	modified_on TIMESTAMP,
	created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'CST'),
	FOREIGN KEY(customer_id) REFERENCES customer(id)
);
INSERT INTO base_order(status,customer_id) VALUES ('AWAITING_CONFORMATION', 1);
INSERT INTO base_order(status,customer_id) VALUES ('DELIVERED', 2);



CREATE TABLE item (
    id  SERIAL PRIMARY KEY,
	name VARCHAR (50),
	price DECIMAL,
    tax DECIMAL
);
INSERT INTO item(name,price,tax) VALUES ('Laptop-i5', 399, 39.9);
INSERT INTO item(name,price,tax) VALUES ('Laptop-i7', 499, 49.9);
INSERT INTO item(name,price,tax) VALUES ('Phone', 899, 71.24);
select * from item;



CREATE TABLE address (
    id  SERIAL PRIMARY KEY,
    line_one VARCHAR (100),
	line_two VARCHAR (100),
	city VARCHAR (30),
	state VARCHAR (30),
	zip  VARCHAR (8),
    type VARCHAR (20),
	created_on TIMESTAMP WITHOUT TIME ZONE DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'CST')
);
INSERT INTO address(line_one, line_two, city, state, zip) VALUES ('St bee Street','North Way zone','Manhatten','NewYork','70023');
INSERT INTO address(line_one, line_two, city, state, zip) VALUES ('Le stray street','Hoods way','Long Island','NewYork','70025');





CREATE TABLE sub_order(
	id SERIAL PRIMARY KEY,
	base_order_id INTEGER,
  	item_id INTEGER,
  	qty INTEGER,
	shipping_address_id INTEGER,
	order_status VARCHAR (25),
    modified_on TIMESTAMP,
	created_on TIMESTAMP WITHOUT TIME ZONE DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'CST'),
	FOREIGN KEY(base_order_id) REFERENCES base_order(id),
	FOREIGN KEY(item_id) REFERENCES item(id),
	FOREIGN KEY(shipping_address_id) REFERENCES address(id)
);
INSERT INTO sub_order(base_order_id,item_id,qty,shipping_address_id,order_status) VALUES (1,2,1,2,'DELIVERED');
INSERT INTO sub_order(base_order_id,item_id,qty,shipping_address_id,order_status) VALUES (1,3,1,2,'DELIVERED');



CREATE TABLE payment(
	id SERIAL PRIMARY KEY,
	pay_mode VARCHAR (25),
  	confirmation_number INTEGER,
  	amount DECIMAL,
 	base_order_id INTEGER,
    billing_address_id INTEGER,
    modified_on TIMESTAMP,
  	created_on TIMESTAMP WITHOUT TIME ZONE DEFAULT (CURRENT_TIMESTAMP AT TIME ZONE 'CST'),
    status VARCHAR (25) DEFAULT 'TO_BE_PROCESSED',
	FOREIGN KEY(base_order_id) REFERENCES base_order(id),
    FOREIGN KEY(billing_address_id) REFERENCES address(id)
);
INSERT INTO payment(pay_mode,confirmation_number,amount,base_order_id, billing_address_id) VALUES ('ONLINE_CARD',24578,438.9,1,2);


--QUERY-1, PayMent Status for the Base Order.

select base_order.id as ORDER_ID, payment.pay_mode as MODE_OF_PAYMENT, payment.amount as TOTAL_AMOUNT from base_order join payment on payment.base_order_id = base_order.id where base_order.id = 1;
