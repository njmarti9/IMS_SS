drop table if exists PRODUCTS;
drop table if exists WAREHOUSES;
drop table if exists USERS;
drop table if exists COMPANIES;

create table COMPANIES (
	id SERIAL PRIMARY KEY,
	company_name VARCHAR(50)
);

create table USERS (
	id SERIAL PRIMARY KEY,
	username VARCHAR(50),
	company_id INT,
	FOREIGN KEY (company_id) REFERENCES COMPANIES(id)
);

create table WAREHOUSES (
	id SERIAL PRIMARY KEY,
	warehouse_name VARCHAR(50),
	location VARCHAR(50),
	capacity REAL,
	company_id INT,
	FOREIGN KEY (company_id) REFERENCES COMPANIES(id)
);

create table PRODUCTS (
	id SERIAL PRIMARY KEY,
	product_name VARCHAR(50),
	quantity INT,
	size REAL,
	price REAL,
	warehouse_id INT,
	FOREIGN KEY (warehouse_id) REFERENCES WAREHOUSES(id)
);
