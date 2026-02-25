# Transaction Handling Service (THT)

This is a Spring Boot application designed to manage Customers, Accounts, and Transactions. It follows a clean architecture with a package-by-feature structure and implements best practices for RESTful API development.

## 🚀 Features

- **Customer Management**: Create customers and retrieve their transaction history and balances.
- **Account Management**: Create accounts linked to customers.
- **Transaction Processing**: Handle deposits, withdrawals, and transfers between accounts.
- **Data Integrity**: Uses Flyway for database migrations and transactional operations.
- **Standardized Responses**: Consistent API response format using a generic wrapper.
- **Global Exception Handling**: Centralized error handling for validation and business exceptions.

## 🛠 Tech Stack

- **Java**: 17
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL
- **Migration**: Flyway
- **Build Tool**: Maven
- **Utilities**: Lombok
- **Testing**: JUnit 5, MockMvc

## 📂 Project Structure

The project follows a **Package by Feature** structure for better scalability and maintainability:

```
src/main/java/com/robbyari/tht
├── common               # Shared utilities, exceptions, and DTOs
├── feature
│   ├── account          # Controller, Service, Repository, Entity
│   ├── customer         # Controller, Service, Repository, Entity
│   └── transaction      # Controller, Service, Repository, Entity
└── ThtApplication.java  # Main application class
```

## ⚙️ Setup & Installation

### Prerequisites

- Java 17 or higher
- PostgreSQL Database
- Maven (optional, wrapper included)

### Configuration

1.  **Clone the repository**:
    ```bash
    git clone <repository-url>
    cd tht
    ```

2.  **Configure Database**:
    Update `src/main/resources/application.yaml` (or `application.properties`) with your PostgreSQL credentials. 
    It is recommended to use Environment Variables:
    ```yaml
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/your_db_name
        username: your_postgres_username
        password: your_postgres_password
    ```

3.  **Run Migrations**:
    Flyway will automatically migrate the database schema on startup.

4.  **Build and Run**:
    ```bash
    ./mvnw spring-boot:run
    ```

## 📚 API Documentation

### 1. Customers

#### Create Customer
**Endpoint**: `POST /api/v1/customers`

**Request Body**:
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "081234567890",
  "address": "Jakarta, Indonesia"
}
```

**Response**:
```json
{
  "status": 201,
  "message": "Customer created successfully",
  "data": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phoneNumber": "081234567890",
    "address": "Jakarta, Indonesia",
    "createdAt": "2023-10-27T10:00:00",
    "updatedAt": "2023-10-27T10:00:00"
  }
}
```

#### Get Customer Transactions
**Endpoint**: `GET /api/v1/customers/{customerId}/transactions`

**Response**:
```json
{
  "status": 200,
  "message": "Customer transactions retrieved successfully",
  "data": [
    {
      "accountType": "SAVINGS",
      "accountNumber": "1234567890",
      "amount": 50000.00,
      "transactionType": "DEPOSIT",
      "transactionReference": "REF-12345",
      "transactionDate": "2023-10-27T10:30:00",
      "customerName": "John Doe",
      "customerEmail": "john.doe@example.com",
      "customerPhoneNumber": "081234567890",
      "targetAccountNumber": null,
      "targetCustomerName": null
    }
  ]
}
```

#### Get All Customer Balances
**Endpoint**: `GET /api/v1/customers/balances`

**Response**:
```json
{
  "status": 200,
  "message": "Customer balances retrieved successfully",
  "data": [
    {
      "customerId": 1,
      "customerName": "John Doe",
      "customerEmail": "john.doe@example.com",
      "balance": 150000.00
    }
  ]
}
```

### 2. Accounts

#### Create Account
**Endpoint**: `POST /api/v1/accounts`

**Request Body**:
```json
{
  "customerId": 1,
  "initialBalance": 100000,
  "accountType": "SAVINGS"
}
```

**Response**:
```json
{
  "status": 201,
  "message": "Account created successfully",
  "data": {
    "id": 1,
    "accountNumber": "1234567890",
    "balance": 100000,
    "accountType": "SAVINGS",
    "status": "ACTIVE",
    "customerId": 1,
    "customerName": "John Doe",
    "createdAt": "2023-10-27T10:15:00"
  }
}
```

### 3. Transactions

#### Create Transaction
**Endpoint**: `POST /api/v1/transactions`

**Request Body (Deposit/Withdrawal)**:
```json
{
  "accountId": 1,
  "amount": 50000,
  "transactionType": "DEPOSIT",
  "description": "Initial Deposit"
}
```

**Request Body (Transfer)**:
```json
{
  "accountId": 1,
  "targetAccountId": 2,
  "amount": 25000,
  "transactionType": "TRANSFER",
  "description": "Payment for lunch"
}
```

**Response**:
```json
{
  "status": 201,
  "message": "Transaction created successfully",
  "data": {
    "id": 1,
    "transactionReference": "REF-12345",
    "accountId": 1,
    "accountNumber": "1234567890",
    "targetAccountId": 2,
    "targetAccountNumber": "0987654321",
    "amount": 25000,
    "transactionType": "TRANSFER",
    "description": "Payment for lunch",
    "transactionDate": "2023-10-27T12:00:00"
  }
}
```

## 📝 Response Format

All API responses follow a standard format:

**Success:**
```json
{
  "status": 200,
  "message": "Operation successful",
  "data": { ... }
}
```

**Error:**
```json
{
  "status": 400,
  "message": "Error description",
  "data": null
}
```
