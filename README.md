# 📋 Task Manager REST API

A secure REST API built using **Spring Boot** that allows users to manage their personal tasks. The application implements **JWT Authentication**, **Spring Security**, **MySQL**, Bean Validation, and Swagger/OpenAPI while following a clean layered architecture.

---

## ✨ Features

- 🔐 User Registration & Login
- 🔑 JWT Authentication
- 👤 User-specific Authorization
- 📝 Task CRUD Operations
- ✅ Bean Validation
- ⚠️ Global Exception Handling
- 📖 Swagger/OpenAPI Documentation

---

## 🛠 Tech Stack

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- JWT
- Swagger/OpenAPI
- Maven

---

## 🏗 Architecture

```
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
MySQL
```

---

## 🔐 Authentication

Users authenticate using JWT.

After logging in, the client receives a JWT token which must be sent with every protected request.

```
Authorization: Bearer <token>
```

---

## 📖 API Endpoints

### Authentication

| Method | Endpoint |
|--------|----------|
| POST | /auth/register |
| POST | /auth/login |

### Tasks

| Method | Endpoint |
|--------|----------|
| POST | /tasks |
| GET | /tasks |
| GET | /tasks/{id} |
| PUT | /tasks/{id} |
| DELETE | /tasks/{id} |

---

## 🚀 Running the Project

1. Clone the repository
2. Configure MySQL in `application.properties`
3. Run:

```bash
mvn spring-boot:run
```

---

## 📚 Concepts Implemented

- Spring Security
- JWT Authentication
- BCrypt Password Encoding
- Layered Architecture
- DTO Pattern
- Bean Validation
- Global Exception Handling
- Swagger Documentation

---

## 🔮 Future Improvements

- Docker
- Cloud Deployment
- Refresh Tokens
- Pagination
- Sorting & Filtering
