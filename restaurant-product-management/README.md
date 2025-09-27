# 🛠️ Restaurant Product Management

A backend REST API built with **Java 17** and **Spring Boot 3** to manage restaurant products and categories.  
The system includes secure JWT-based authentication and role-based authorization for `USER` and `ADMIN` roles.  
Admins can create and manage product data, while users can view resources in a read-only manner. Deployed using Docker, Kubernetes (Minikube), and Render (CI/CD pipeline integration)

---

## 📌 Core Functionality

✅ User registration & login with JWT  
✅ Role-based access control (`ADMIN` / `USER`)  
✅ Create, update, delete products and categories (ADMIN only)  
✅ View products and categories (USER & ADMIN)  
✅ PostgreSQL database integration  
✅ Centralized exception handling  
✅ Dockerized and Kubernetes-ready  
✅ API tested with Postman
✅ Swagger API Documentation
✅ CI/CD pipeline with Render

---

## 🧰 Tech Stack

- Java 17
- Spring Boot (Web, Security, Data JPA)
- Spring Security + JWT
- PostgreSQL
- Maven
- Docker, Docker Compose
- Kubernetes (Minikube)
- Swagger (springdoc-openapi)
- Render (CI/CD Deployment)
- Postman
- Git & GitHub

---

## 📁 Project Structure

```
restaurant-product-management/
├── src/
│   └── main/
│       ├── java/com/restaurant/productmanagement/
│       │   ├── config/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── enums/
│       │   ├── mapper/
│       │   ├── model/
│       │   ├── repository/
│       │   ├── security/
│       │   ├── service/
│       │   └── serviceImpl/
│       └── resources/
│           ├── static/
│           ├── templates/
│           └── application.properties
├── Dockerfile
├── docker-compose.yml
├── postgres-deployment.yaml
├── postgres-service.yaml
├── restaurant-app-deployment.yaml
├── service.yaml
├── pom.xml
├── README.md



---

## 🚀 Getting Started

### Prerequisites

- Java 17
- Maven
- Docker & Docker Compose
- Minikube (Kubernetes)

### Clone the Repository

```bash
git clone https://github.com/Pashayeva-Xadija/restaurant-product-management.git
cd restaurant-product-management
```

### Run with Docker

```bash
docker-compose up --build
```

### Deploy on Minikube

```bash
kubectl apply -f postgres-deployment.yaml
kubectl apply -f restaurant-app-deployment.yaml
kubectl apply -f service.yaml
```

---

## 🔐 Authentication & Roles

| Endpoint            | Description               |
|---------------------|---------------------------|
| `POST /auth/register` | Register a new user       |
| `POST /auth/login`    | Login & receive JWT token |

Use your token in requests:

```http
Authorization: Bearer <token>
```

**Roles:**
- `ADMIN`: Full access to CRUD endpoints
- `USER`: Read-only access

---

## 📬 Contact

Made with ❤️ by **Xədicə Paşayeva**  
📧 xadijapashayeva@gmail.com  
🔗 [LinkedIn](https://www.linkedin.com/in/xadija-pashayeva)

---


