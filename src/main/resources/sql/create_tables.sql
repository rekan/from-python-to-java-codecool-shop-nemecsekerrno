DROP TABLE IF EXISTS supplier, product, productcategory, customer;

CREATE TABLE supplier
(
  s_id serial PRIMARY KEY,
  s_name varchar(40),
  s_description varchar(100)
);

CREATE TABLE productcategory
(
  c_id serial PRIMARY KEY,
  c_name VARCHAR(30),
  c_department varchar(250),
  c_description varchar(250)
);

CREATE TABLE product
(
  p_id serial PRIMARY KEY,
  p_name varchar(40),
  p_defaultprice DECIMAL(19,4),
  p_defaultcurrency varchar(10),
  p_description varchar(250),
  p_productcategory INTEGER,
  p_supplier INTEGER
);

CREATE TABLE customer
(
  cust_id serial PRIMARY KEY,
  cust_first_name VARCHAR(30),
  cust_last_name VARCHAR(30),
  cust_email VARCHAR(100),
  cust_pw VARCHAR (100)
);

