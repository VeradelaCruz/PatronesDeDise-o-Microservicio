# Online Shopping System

This project is an online shopping system built using a microservices architecture. It allows users to browse products, manage a shopping cart, make payments, and complete purchases. The project is designed to showcase the usage of microservices, service discovery, and API Gateway with Spring Boot and Spring Cloud.

## 🚀 Technologies Used

- **Java 17+** - The main programming language used for backend development.
- **Spring Boot** - Framework used for building stand-alone, production-grade Spring-based applications.
- **Spring Cloud** - For microservices management (Eureka for service discovery, OpenFeign for inter-service communication, and Spring Cloud Gateway for routing).
- **MySQL** - Relational database for storing products, user data, and transaction records.
- **Maven** - For managing project dependencies and building the project.
- **Docker** (optional) - Containerization of the services for easier deployment and scaling.
- **Git** & **GitHub** - For version control and project collaboration.

## 📁 Repository Structure

This is a monorepo containing the following services:

- **`/catalog-service`** — Handles the product catalog, allowing users to browse and view product details.
- **`/paymentMethod-service`** — Manages payment methods and processes transactions.
- **`/eureka-service`** — Service discovery, allowing microservices to register and discover each other.
- **`/purchaseManagment-service`** — Manages the purchase process, from order creation to finalizing the payment.
- **`/shoppingCart-service`** — Manages the shopping cart, allowing users to add/remove items and check out.

Each service is independent but communicates with others through REST APIs.

## ⚙️ How to Run the Project Locally

To run the project locally, follow these steps:

### Step 1: Clone the Repository
```bash
git clone https://github.com/VeradelaCruz/PatronesDeDise-o-Microservicio.git
```


### Step 2: Set Up Databases
Ensure you have MySQL running and create the necessary databases for each service. Update the application.properties
or application.yml for each service with the correct database configurations.
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/catalog_db
spring.datasource.username=root
spring.datasource.password=password
```

### Step 3: Run Eureka Service
Start the eureka-service first. This will register all other services for discovery.
```bash
cd eureka-service
mvn spring-boot:run
```

### Step 4: Start Other Services
Start the remaining services. Each microservice can be started individually:
```bash
cd catalog-service
mvn spring-boot:run

cd paymentMethod-service
mvn spring-boot:run

cd purchaseManagment-service
mvn spring-boot:run

cd shoppingCart-service
mvn spring-boot:run
```

### Step 5: Test the APIs
Once the services are up and running, you can test the APIs using Postman or any other API testing tool. 
Make sure that the eureka-service is running, as the other services will register themselves with Eureka for service discovery.

## 📜 API Documentation

### 📦 Catalog Service

- GET  http://localhost:8081/catalog/getAllProducts - Get a list of all products.
- GET http://localhost:8081/catalog/getById/{productId} - Get a product by id.
- POST http://localhost:8081/catalog/addProduct - Add a product to database.
  JSON format:
```bash
[
  {
    "nameProduct": "Product 1",
    "category": "Category 1",
    "stock": 50
  },
  {
    "nameProduct": "Product 2",
    "category": "Category 2",
    "stock": 30
  }, ...
]
```
- PUT http://localhost:8081/catalog/updateProduct/{productId} - Update a product.
```bash
{
  "nameProduct": "Updated Product Name",
  "category": "Updated Category",
  "stock": 40
}

```
- DELETE http://localhost:8081/catalog//deleteProduct/{productId} - Remove a product.

### 🛒 Shopping Cart Service
- GET  http://localhost:8082/cart/getAllCarts - Get a list of all carts.
- GET http://localhost:8081/cart/getCartById/{cartId} - Get a cart by id.
- POST http://localhost:8082/cart/createCart - Create a cart with products and add it to database.
```bash
{
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ]
}

```
- DELETE http://localhost:8082/cart/deleteCart/{cartId} - Remove a cart.

### 💳 Payment Method Service
This microservice handles everything related to payments and available payment methods. It includes two main entities:

### PaymentMethod: Represents the different types of payment methods available, such as credit card, PayPal, bank transfer, etc.
- GET   http://localhost:8083/paymentMethod/getAllMethods - Get a list of all payment methods.
- GET  http://localhost:8083/paymentMethod/getById/{paymentMethodId} - Get a payment by id.
- POST http://localhost:8083/paymentMethod/add - Add a payment method to the data base.
```bash
{
  "methodName": "Credit Card",
  "provider": "Visa"
}

```
- DELETE http://localhost:8083/paymentMethod/remove/{paymentMethodId} - Remove a peyment.

  
### Payment: Represents a payment transaction, including details like the selected payment method, the amount, and the associated purchase.
- GET   http://localhost:8083/payment/getAllPayments - Get a list of all paid carts.
- GET  http://localhost:8083/payment/getPaymentById/{paymentId} - Get a cart payment by id.
- GET http://localhost:8083/payment/getPaymentByCartId/{cartId} - Get a cart payment by cart id.
- POST  http://localhost:8083/payment/payCart - Pay a cart and add it to data base.
```bash
{
  "cartDTO": {
    "cartId": 1
  },
  "paymentMethod": {
    "paymentMethodId": 2,
    "methodName": "Credit Card",
    "provider": "Visa"
  }
}

```
### 🧾 Purchase Management Service
- POST http://localhost:8084/purchase/paidPurchases - Register an already paid purchase.
```bash

    "cartDTO": {
        "cartId": 2
    },
    "paymentMethod": {
        "paymentMethodId": 3,
        "methodName": "Debit Card"
    }
}
```
