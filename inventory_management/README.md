# Inventory Management Service

This project was created to make a rudementary full-stack solution to managing company warehouses.

## Description

  This project allows for a company's users to view and manipulate data regarding the warehouses and products associated with the specific company that a user belongs to. This includes all of the standard CRUD functionality to Create, Read, Update, and Delete the warehouses and the products in the warehouses as well.

## Getting Started

### Dependencies

* Apache Maven (3.9.6)
* Java (17.0.10)
* Spring Boot
* Node.js (v20.12.0)
* Built on Windows 10
* Postgres for Database

### Installing

* Clone repository from https://github.com/njmarti9/IMS_SS
* Make sure to have all dependencies and environment variables installed and created
* Update the application.yaml to accomodate a new database 
* (Can use the provided .sql queries but some of the products in their warehouses will exceed the capacity unless corrected)

### Executing program

* I used live-server in vscode to start the front-end of the project
* For the backend, you will need to run the spring boot application with the command below
```
mvn spring-boot:run
```

## Help

* Make sure all environement variables are correct for the dependencies that require them
* Change the password and potentially the username and ports for the database and live-server

## Authors

Noah Martin