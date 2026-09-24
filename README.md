Bank System

A console-based banking management system developed using Core Java, Java Collections Framework, and Java 8 Stream API.

Overview

Bank System is a console-based application that demonstrates basic banking operations using Core Java and Object-Oriented Programming principles.

The application manages customers, accounts, and transactions through a console interface.

The application uses in-memory data storage. No database is used, so data is lost when the application is stopped or restarted.

Features

Customer management

Account management

Account creation

Deposit operations

Withdrawal operations

Transaction management

Searching and filtering data

In-memory data management

Console-based interaction

Technologies Used

Core Java

Java Collections Framework

Java 8 Stream API

Object-Oriented Programming

Project Structure
src/
└── app/
├── Main.java
│
├── domain/
│   ├── Account.java
│   ├── Customer.java
│   ├── Transaction.java
│   └── Type.java
│
├── repository/
│   ├── AccountRepository.java
│   ├── CustomerRepository.java
│   └── TransactionRepository.java
│
└── service/
├── BankService.java
└── impl/
└── BankServiceImpl.java

Architecture

The project follows a layered structure.

Domain

Contains the main entities of the banking system:

Account

Customer

Transaction

Type

Repository

Responsible for managing application data using Java Collections:

AccountRepository

CustomerRepository

TransactionRepository

Service

Contains the banking business logic:

BankService

BankServiceImpl

Java Concepts Demonstrated
Object-Oriented Programming

Encapsulation

Abstraction

Interfaces

Classes and objects

Separation of responsibilities

Collections Framework

Java Collections are used to store and manage customers, accounts, and transactions during application execution.

Java 8 Stream API

Stream API is used for searching, filtering, and processing collection data.

Data Storage

This application does not use a database.

Data is stored in memory using Java Collections.

Application starts
↓
Data is stored in memory
↓
Banking operations are performed
↓
Application stops
↓
Data is lost

How to Run
Requirements

Java 8 or later

IntelliJ IDEA or another Java IDE

Steps

Clone the repository.

Open the project in IntelliJ IDEA.

Open Main.java.

Run the main() method.

Follow the instructions in the console.

Future Enhancements

Database integration

Persistent data storage

Custom exception handling

Input validation

Unit testing using JUnit

Transaction history and reporting

REST API using Spring Boot

Author

Riyan