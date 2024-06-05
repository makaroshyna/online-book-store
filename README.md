![Page Glide Logo](images/page_glide_logo.png)

Building an online bookstore requires a strong foundation. 
Page Glide was created to empower developers with a well-structured and efficient backend API 
for their online bookstore applications.

## Introduction
Page Glide is a backend API built with Spring Boot for rapid development. 
It leverages Spring Security for secure user authentication and authorization. 
Spring Data JPA provides seamless interaction with a MySQL database for data persistence. 
Swagger is integrated for clear and comprehensive API documentation. 
Docker enables easy containerization for deployment.

### Key features

* User Authentication and Authorization (Registration, Login)
* Book Management (CRUD operations, searching)
* Category Management (CRUD operations)
* Shopping Cart Management (adding, updating, removing items)
* Order Management (placing orders, viewing order history, order details)
* User Role Management (Admin vs. User functionalities)
* Unit & Integration API Testing (ensuring code reliability)

## Technologies
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


## Installation

### 1. Prerequisites

**Installation without Docker:**
* Java 21+
* Maven 4+
* MySQL 8+

**Installation with Docker:**
* Docker

### 2. Clone the repository
```bash
git clone https://github.com/makaroshyna/online-book-store.git
cd online-book-store
```

## Running the project

**Without Docker**

### 1. Set up MySQL
Establish a new MySQL database, recording its URL, username, and password for future reference.

### 2. Configure environment variables
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

### 3. Get set up
Run the following command to install any required dependencies and build your project:
```bash
mvn clean install
```

### 4. Start the server
Once the build is complete, use this command to run your application:
```bash
mvn spring-boot:run
```
Your server will be accessible at `http://localhost:8080`.

**With Docker**

### 1. Configure environment variables
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

### 2. Install dependencies and build the project
```bash
mvn clean install
```

### 3. Build and run the Docker containers
```bash
docker compose up
```

## Testing
Using the following command you can run tests:
```bash
mvn test
```
Page Glide utilizes Mockito for mocking dependencies and 
JUnit for unit testing the application logic. 
This ensures code reliability and maintainability.

## Functionality
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

## Author
* LinkedIn: [Katya Makarchuk](https://www.linkedin.com/in/katya-makarchuk-a89bab217/)
* GitHub: [makaroshyna](https://github.com/makaroshyna)
