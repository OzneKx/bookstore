# 📚 Bookstore API

RESTful API for managing books, built with **Java 21 + Spring Boot 3**, featuring JWT authentication, **Swagger/OpenAPI** documentation, **Docker + PostgreSQL**.

This project demonstrates **backend best practices** (DTO/Mapper layer, global exception handler, unit & integration testing, JWT security, environment profiles `dev`/`prod`) and serves as a professional portfolio reference.

---

## Technologies Used
- **Java 21**
- **Spring Boot 3.2.5**
    - Validation
    - Spring Web
    - Spring Data JPA (Hibernate)
    - Spring Security (JWT + ROLE_USER | ROLE_ADMIN)
- **MapStruct** (DTO ↔ Entity)
- **Docker + Docker Compose**
- **H2 Database** (development environment)
- **JUnit 5 + Mockito** (unit tests + integration tests)
- **PostgreSQL 17** (production environment, via Docker)
- **Swagger / OpenAPI 3** (JWT token + Authorize button)

---

## 📂 Project Structure
```
bookstore/
├── src/main/java/com/application/bookstore
│   ├── controller   → REST Controllers (Books, Auth)
│   ├── dto          → DTOs (BookRequest, BookResponse, AuthRequest, AuthResponse)
│   ├── entity       → JPA Entities (Book, User)
│   ├── mapper       → MapStruct (Entity ↔ DTO conversion)
│   ├── repository   → Spring Data JPA Repositories
│   ├── security     → JWT configuration, filters, UserDetails
│   ├── service      → Business logic layer
│   └── exception    → Centralized error handling (400, 404, 500)
│
├── src/test/java/com/application/bookstore
│   ├── integration  → Integration tests
│   └── unit         → Unit tests for services and controllers
│
├── src/main/resources
│   ├── application.properties       → Base configuration
│   ├── application-dev.properties   → DEV (H2)
│   ├── application-test.properties  → TEST (H2)
│   └── application-prod.properties  → PROD (Postgres via Docker)
│
├── Dockerfile
├── docker-compose.yml
└── README.md
```

---

## 🔑 Authentication & Authorization
- Authentication via **JWT Token** (`/auth/login`).
- Users have roles:
    - **ROLE_USER** → can create/list books.
    - **ROLE_ADMIN** → can delete books.
- JWT must be sent in the request header:
```http
Authorization: Bearer <token>
```

### Example Login
**Request:**
```http
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "123456"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

## Endpoints

### Authentication
- `POST /auth/login` → returns JWT

### Books
- `GET /api/books` → list all books
- `POST /api/books` → create new book
- `GET /api/books/{id}` → get book by ID
- `DELETE /api/books/{id}` → delete book (ROLE_ADMIN only)

---

## Swagger (OpenAPI)
Once running, interactive documentation is available at:

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Features:
- Test endpoints
- Authenticate with JWT via **Authorize** button

---

## 🐳 Running with Docker

### 1. Build and start containers
```bash
docker-compose up --build
```

### 2. Access the API
- API → [http://localhost:8080](http://localhost:8080)
- Swagger UI → [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 3. Stop containers
```bash
docker-compose down
```

---

## Environment Profiles

### Dev (default)
Database: **H2** (local file `./data/bookstore_db.mv.db`)  
Config: `application-dev.properties`

### Prod (Docker/Postgres)
Database: **Postgres 17** running in container `db`  
Credentials defined via env variables (`docker-compose.yml`):
```yaml
POSTGRES_DB=bookstore
POSTGRES_USER=user
POSTGRES_PASSWORD=user1234
```

Spring config (`application-prod.properties`):
```properties
spring.datasource.url=jdbc:postgresql://db:5432/bookstore
spring.datasource.username=${POSTGRES_USER}
spring.datasource.password=${POSTGRES_PASSWORD}
```

---

## Testing

Run all tests:
```bash
mvn test
```

Covers:
- **Unit tests** (Mockito)
- **Integration tests** (JWT auth, role restrictions)

Examples:
- Regular user cannot delete a book → `403 Forbidden`
- Admin deletes book successfully → `204 No Content`

---

📌 This README demonstrates the full cycle: development → build → Docker → deploy, showing professional backend practices.
