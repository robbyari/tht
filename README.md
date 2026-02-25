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
    Update `src/main/resources/application.yaml` (or `application.properties`) with your PostgreSQL credentials:
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

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/customers` | Create a new customer |
| `GET` | `/api/v1/customers/{customerId}/transactions` | Get transaction history for a customer |
| `GET` | `/api/v1/customers/balances` | Get total balance for all customers |

**Example Request (Create Customer):**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phoneNumber": "081234567890",
  "address": "Jakarta, Indonesia"
}
```

### 2. Accounts

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/accounts` | Create a new account for a customer |

**Example Request (Create Account):**
```json
{
  "customerId": 1,
  "accountType": "SAVINGS"
}
```

### 3. Transactions

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/api/v1/transactions` | Perform a transaction (DEPOSIT, WITHDRAWAL, TRANSFER) |

**Example Request (Transfer):**
```json
{
  "accountId": 1,
  "targetAccountId": 2,
  "amount": 50000,
  "transactionType": "TRANSFER",
  "description": "Payment for services"
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
