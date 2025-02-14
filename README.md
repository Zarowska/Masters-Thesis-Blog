Overview
This project is a prototype social media application designed to compare the performance, data retrieval efficiency, and scalability of REST and GraphQL APIs.
This application was created as part of a master’s thesis at Warsaw University of Technology.

Features
Supports both REST and GraphQL APIs.
Uses Spring Boot with PostgreSQL for data storage.
Implements JWT authentication for security.
Includes performance tests to analyze API efficiency.

Technologies Used
Backend: Spring Boot, GraphQL Java, Spring Security, Hibernate, PostgreSQL
Build Tool: Gradle
Testing: JUnit, Testcontainers

Installation

Before running the application, make sure you have installed:
Java 21
Gradle
Docker (to run the database)

How to Run the Application
1. Clone the repository:
git clone https://github.com/Zarowska/Masters-Thesis-Blog.git
cd Masters-Thesis-Blog

2. Start the database using Docker:
docker-compose up -d

 3. Build and start the application:
./gradlew build
./gradlew bootRun

4. Access the APIs:
REST API: http://localhost:8080/api/...
GraphQL API: http://localhost:8080/graphql
