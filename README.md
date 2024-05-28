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
* Unit Testing (ensuring code reliability)

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


## Installation

### 1. Prerequisites
* Java 21+
* Maven 4+
* Docker
* MySQL 8+

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

### 4. Install dependencies and build the project
```bash
mvn clean install
```

### 5. Build and run the Docker containers
```bash
docker compose up
```
This command will start MySQL and application containers, 
that make the application available on `http://localhost:8080`.

## Testing
Using the following command you can run tests:
```bash
mvn test
```
Page Glide utilizes Mockito for mocking dependencies and 
JUnit for unit testing the application logic. 
This ensures code reliability and maintainability.

## Functionality
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
