# Receipt Processor API

This project implements a web service that processes receipts and calculates points based on a set of business rules. It is built with Java, Spring Boot, and Spring Data JPA, and uses an H2 in-memory database. The project is also Dockerized for containerized deployment.

## Overview

The Receipt Processor API exposes two main endpoints:

- **POST `/receipts/process`**  
  Accepts a JSON receipt payload, stores it in the H2 database, and returns a generated receipt ID (UUID).

- **GET `/receipts/{id}/points`**  
  Retrieves the receipt by its ID, calculates points based on business rules, and returns the points.

For more information, visit the [SwaggerHub]https://app.swaggerhub.com/apis/northeasternuniversi-18a/receipt-processor/1.0.0#/default/post_receipts_process.

## Project Structure
├── .github/
│   └── workflows/
│       └── maven-build.yml
├── .idea/
├── .mvn/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── springdemo
│   │   │           └── receiptprocessoronline
│   │   │               ├── ReceiptProcessorOnlineApplication.java
│   │   │               ├── exception
│   │   │               │   ├── ExceptionHandlerAdvice.java
│   │   │               │   ├── ObjectNotFoundException.java
│   │   │               ├── receipt
│   │   │               │   ├── ReceiptController.java
│   │   │               │   ├── Receipt.java
│   │   │               │   ├── ReceiptItem.java
│   │   │               │   ├── ReceiptRepository.java
│   │   │               │   ├── ReceiptService.java
│   │   │               ├── system
│   │   │               │   ├── DBDataInitializer.java
│   │   │               │   ├── IdData.java
│   │   │               │   ├── PointsData.java
│   │   │               │   ├── Result.java
│   │   │               │   ├── StatusCode.java
│   │   ├── resources
│   │   │   ├── static/
│   │   │   ├── templates/
│   │   │   ├── application.yml
│   │   │   ├── data.sql  # (Optional for preloading test data)
│   ├── test
│   │   ├── java
│   │   │   └── com
│   │   │       └── springdemo
│   │   │           └── receiptprocessoronline
│   │   │               ├── receipt
│   │   │               │   ├── ReceiptControllerTest.java
│   │   │               │   ├── ReceiptServiceTest.java
│   │   │               ├── ReceiptProcessorOnlineApplicationTests.java
├── target   
├── images
├── .gitattributes
├── .gitignore
├── Dockerfile
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
├── External Libraries/
└── Scratches and Consoles/

## Design Mind Map
The receipt processor is designed based on the following components:
![Receipt Processor Mind Map](images/design-mindmap.png)


