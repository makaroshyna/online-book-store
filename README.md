![Page Glide Logo](images/page_glide_logo.png)

Building an online bookstore requires a strong foundation. 
Page Glide was created to empower developers with a well-structured and efficient backend API 
for their online bookstore applications.

## 🚀 Introduction

Page Glide is a backend API built with Spring Boot for rapid development. 
It leverages Spring Security for secure user authentication and authorization. 
Spring Data JPA provides seamless interaction with a MySQL database for data persistence. 
Swagger is integrated for clear and comprehensive API documentation. 
Docker enables easy containerization for deployment.

## 📖 Table of Contents

* [Introduction](#introduction)
* [Key features](#key-features)
* [Technologies](#technologies)
* [Installation without Docker](#installation-without-docker)
* [Installation with Docker](#installation-with-docker)
* [Testing](#testing)
* [Visual Overview](#visual-overview)
* [Functionality](#functionality)
* [Author](#author)

## ⛓ Key features

* User Authentication and Authorization (Registration, Login)
* Book Management (CRUD operations, searching)
* Category Management (CRUD operations)
* Shopping Cart Management (adding, updating, removing items)
* Order Management (placing orders, viewing order history, order details)
* User Role Management (Admin vs. User functionalities)
* Unit & Integration API Testing (ensuring code reliability)

## 🔨 Technologies

* **Spring Boot**: framework for building Java-based web applications
* **Spring Security**: security and user management
* **Spring Data JPA**: data persistence
* **Swagger**: API documentation
* **Docker**: deployment and scalability
* **Liquibase**: database management
* **MySQL**: database management system
* **Mockito**: mocking frameworks for tests
* **JUnit**: testing and reliability
* **Testcontainers**: in-memory database for integration testing

## 🤸 Installation without Docker
<details>
<summary>Click for detailed instructions</summary>

### 1. Prerequisites

* Java 21+
* Maven 4+
* MySQL 8+

### 2. Clone the repository

```bash
git clone https://github.com/makaroshyna/online-book-store.git
cd online-book-store
```

### 3. Set up MySQL

Establish a new MySQL database, recording its URL, username, and password for future reference.

### 4. Configure environment variables

Create a file names `application.properties` in `src/main/resources` with the following context
(replace placeholders with your details):
```properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
server.servlet.context-path=/api

jwt.expiration=token_expiration_time
jwt.secret=your_secret_key
```

### 5. Get set up

Run the following command to install any required dependencies and build your project:
```bash
mvn clean install
```

### 6. Start the server

Once the build is complete, use this command to run your application:
```bash
mvn spring-boot:run
```
Your server will be accessible at `http://localhost:8080`.

</details>

## 🤖 Installation with Docker

<details>
<summary>Click for detailed instructions</summary>
  
### 1. Prerequisites

* Docker

### 2. Clone the repository

```bash
git clone https://github.com/makaroshyna/online-book-store.git
cd online-book-store
```

### 3. Configure environment variables

Create an .env file in the project root directory and add the following:
```env
MYSQLDB_DATABASE=your_db_name
MYSQLDB_USER=your_db_user
MYSQLDB_PASSWORD=your_dbpassword
MYSQLDB_ROOT_PASSWORD=your_db_root_password

MYSQLDB_LOCAL_PORT=3306
MYSQLDB_DOCKER_PORT=3306

SPRING_LOCAL_PORT=8080
SPRING_DOCKER_PORT=8080
DEBUG_PORT=5005
```

### 4. Build and run the Docker containers

```bash
docker compose up --build
```
</details>

## 🔧 Testing

Using the following command you can run tests:
```bash
mvn test
```
Page Glide utilizes Mockito for mocking dependencies and 
JUnit for unit testing the application logic. 
This ensures code reliability and maintainability.

## 👀 Visual Overview

[![Watch on Loom](https://img.shields.io/badge/Watch%20on-Loom-00a4d9)](https://www.loom.com/share/9b03d680b8c44b43ab8eea68902c8650)

## 💻 Postman collection

[![Run in Postman](https://run.pstmn.io/button.svg)](https://www.postman.com/descent-module-geologist-90185526/workspace/page-glide/collection/34368037-138c5745-cf6b-4e23-8e19-2e6d217e7d4d?action=share&creator=34368037)

## 🪄 Functionality

* After running the app, open your browser and go to 
`http://localhost:8080/api/swagger-ui.html` 
to access the Swagger API documentation.

* To access endpoints with required ADMIN role, you can use the next credentials:
```json
{
  "email": "bob.dylan@gmail.com",
  "password": "123456789"
}
```
### Authentication controller

* Registering a new user: `POST: /auth/register`
* Login user: `POST: /auth/login`

### Book controller

* Get all books: `GET: /books`
* Get a book by ID: `GET: /books/{id}`
* Get books by search parameters: `GET: /books/search`
* Create a new book: `POST: /books/` {ADMIN}
* Update a book by ID: `PUT: /books/{id}` {ADMIN}
* Delete a book by ID:`DELETE: /api/books/{id}` {ADMIN}

### Category controller

* Get all categories: `GET: /categories`
* Get a category by ID: `GET: /categories/{id}`
* Get books by category ID: `GET: /categories/{id}/books`
* Create a new category: `POST: /categories` {ADMIN}
* Update a category by ID: `PUT: /categories/{id}`; {ADMIN}
* Delete a category by ID: `DELETE: /api/categories/{id}` {ADMIN}

### Shopping cart controller

* Get a shopping cart of a user: `GET: /cart` {USER}
* Add a book to the shopping cart: `POST: /cart` {USER}
* Update a book by cart item ID: `PUT: /cart/cart-items/{cartItemId}` {USER}
* Delete a book by cart item ID: `DELETE: /cart/cart-items/{cartItemId}` {USER}

### Order controller

* Get all orders of the user: `GET: /orders` {USER}
* Place an order: `POST: /orders` {USER}
* Get all books for an order: `GET: /orders/{orderId}/items` {USER}
* Get a book from the order by ID: `GET: /orders/{orderId}/items/{itemId}` {USER}
* Update order status: `PATCH: /orders/{id}` {ADMIN}

## 💁‍♀️ Author

* LinkedIn: [Kateryna Makarchuk](https://www.linkedin.com/in/kateryna-makarchuk-a89bab217)
* GitHub: [makaroshyna](https://github.com/makaroshyna)
