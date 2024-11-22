# P1 Rent-A-Car

A Spring Boot application for managing car rentals.The system supports creating and managing users, cars, and offers, allowing customers request renting cars.

## Features

- **Cars**: View, Create, Update, and Delete cars.
- **Users**: View, Create, Update and Delete users.
- **Offers**: View, Create, Accept and Delete offers.
- **Validation**: Ensures data integrity for cars, users, and offers.
- **API Documentation**: Integrated Swagger UI for easy API exploration.

---

## Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.3.5**
    - Spring Data JDBC
    - Spring Web
    - Spring Validation
    - Flyway for database migrations (on every startUp and shutDown migrations will be executed and also clean after closing the application) [here](src/main/java/com/fmi/master/p1_rent_a_car/config/FlywayShutdownHook.java)
- **H2 Database** (file-based runtime database) 
  - Username: **sa**  &&     Password: **password**
  - http://localhost:8165/h2-console
- **Swagger UI** (springdoc-openapi)
  - http://localhost:8165/swagger-ui/index.html

### Additional Tools
- **Lombok** for reducing boilerplate code
- **Postman** for API testing and documentation [here](p1-rent-a-car.postman_collection.json)
- **JUnit tests** for local testing [here](src/test/java/com/fmi/master/p1_rent_a_car)
---

## Prerequisites

1. **Java**: Ensure Java 17 or higher is installed.
2. **Maven**: Install Maven to manage dependencies.

---

## Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd p1-rent-a-car

2. Install dependencies:
   ```bash
   mvn clean install
   
3. Start the project:
   ```bash
   mvn spring-boot:run

4. The application will be accessible at http://localhost:8165.


