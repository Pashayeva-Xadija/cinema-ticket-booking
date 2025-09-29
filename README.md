🎬 Cinema Ticket Booking System (Microservices)

A fully functional microservices-based cinema ticket booking platform.
Users can browse movies and showtimes, reserve seats, purchase tickets, process payments, and receive confirmations via Email/SMS with QR codes.

⚡ Built with Java 17 & Spring Boot 3, deployed on Docker & Kubernetes, and powered by Kafka & RabbitMQ for event-driven communication.

✨ Key Features

🔐 Authentication & Authorization

JWT-based login/register

Role-based access (USER and ADMIN)

Centralized API Gateway + CORS support

🎥 Showtime Management

Manage movies, halls, showtimes, and pricing (ADMIN)

Browse and filter available shows (USER)

🎟 Ticket Reservation & Issuance

Seat reservation with availability check

Automatic ticket generation with QR code

💳 Payment Processing

Secure payment workflow integration

Reservation linked with payment status

📩 Notifications

Send Email & SMS confirmations for reservations and payments

Event-driven integration via Kafka & RabbitMQ

📊 Monitoring & Documentation

Centralized exception handling

Swagger / OpenAPI documentation

🧰 Tech Stack

Backend: Java 17, Spring Boot 3 (REST API), Spring Security & JWT, Spring Data JPA
Microservices: Spring Cloud Gateway, OpenFeign
Databases: PostgreSQL, Redis (caching)
Messaging: RabbitMQ, Apache Kafka
Deployment: Docker, Docker Compose, Kubernetes (Minikube)
Documentation: Swagger / OpenAPI
Tools: Maven, Git, GitHub, Lombok

🏗 Architecture – Flow
User
  |
  v
+------------------+
|   API Gateway    |
+------------------+
          |
          v
+------------------+          +------------------+
|   Auth Service   |  -->     |  Login/Register  |
+------------------+          |        + JWT     |
          |                   +------------------+
          v
+------------------+          +-----------------------------+
| Showtime Service |  -->     | Movies, halls & showtimes   |
+------------------+          +-----------------------------+
          |
          v
+------------------+
| Booking Service  |  --> Reservation
+------------------+
          |
          v
+------------------+
| Inventory Service|  --> Seat availability check
+------------------+
          |
          v
+------------------+
| Payment Service  |  --> Payment processing
+------------------+
          |
          v
+------------------+                 +-----------------------+
|  Ticket Service  |  --> Ticket     | Notification Service  |
+------------------+      (QR)  -->  | Email/SMS (Kafka/RMQ) |
                                     +-----------------------+


🚀 Getting Started
Prerequisites

Java 17

Maven

Docker & Docker Compose

Minikube (Kubernetes)

RabbitMQ & Kafka

Clone the Repository
git clone https://github.com/<your-username>/cinema-ticket-booking.git
cd cinema-ticket-booking

Run with Docker
docker-compose up --build

Deploy on Kubernetes
kubectl apply -f kubernetes-manifests/

🔐 Authentication

Main Endpoints

Endpoint	Description
POST /auth/register	Register a new user
POST /auth/login	Authenticate & get JWT token
GET /auth/me	Get authenticated user info

Roles

ADMIN → Manage showtimes, pricing, users

USER → Browse shows, make reservations, buy tickets

📬 Contact

Made with ❤️ by Xədicə Paşayeva
📧 xadijapashayeva@gmail.com

🔗 LinkedIn
