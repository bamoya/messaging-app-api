# Messaging App API

This is the backend API for the Messaging App, a real-time messaging application. It is a Spring Boot application that provides a RESTful API for the frontend and uses WebSockets for real-time communication.

For more information about the overall project architecture, see the [root README.md](../../README.md).

## Key Features

*   **Real-time Messaging:** Send and receive messages in real-time using WebSockets.
*   **User Authentication:** Secure authentication and authorization using Keycloak.
*   **Chat Management:** Create and manage group chats.
*   **File Uploads:** Upload and share files.

## Technologies Used

*   **Java 17**
*   **Spring Boot**
*   **Spring Data JPA**
*   **Spring Security (OAuth2)**
*   **Spring Web**
*   **Spring WebSocket**
*   **PostgreSQL**
*   **Flyway**
*   **Lombok**
*   **SpringDoc OpenAPI**

## Project Structure

```
messaging-app-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/bamoya/whatsapp_clone/
│   │   │       ├── controller/      # REST API controllers
│   │   │       ├── service/         # Business logic
│   │   │       ├── repository/      # Data access layer
│   │   │       ├── model/           # JPA entities
│   │   │       ├── security/        # Security configuration
│   │   │       └── ws/              # WebSocket configuration
│   │   └── resources/
│   │       ├── application.yaml # Application configuration
│   │       └── db/migration/    # Flyway database migrations
│   └── test/                    # Unit and integration tests
├── pom.xml                      # Maven project configuration
└── README.md                    # This file
```

## Getting Started

### Prerequisites

*   Java 17
*   Maven
*   PostgreSQL
*   Keycloak

### Configuration

1.  **Database:** Create a PostgreSQL database named `whatsapp_clone` and update the `spring.datasource` properties in the `application.yaml` file with your database credentials.

2.  **Keycloak:**
    *   Set up a Keycloak server.
    *   Create a realm named `whatsapp-clone`.
    *   Create a client for the backend API.
    *   Update the `spring.security.oauth2.resourceserver.jwt.issuer-uri` property in the `application.yaml` file with your Keycloak server's URL.

### Running the Application

You can run the application using the Spring Boot Maven plugin:

```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`.

## API Endpoints

The main API endpoints are:

*   **`POST /api/chats`**: Create a new chat.
*   **`GET /api/chats`**: Get all chats for the current user.
*   **`POST /api/messages`**: Send a new message.
*   **`GET /api/messages/{chatId}`**: Get all messages for a specific chat.
*   **`POST /api/users/upload`**: Upload a file.

For a complete list of endpoints and their details, see the [OpenAPI documentation](#api-documentation).

## WebSocket Communication

The application uses WebSockets for real-time communication. The WebSocket endpoint is `/ws`. Clients can subscribe to topics to receive real-time updates.

The main topics are:

*   `/topic/messages/{chatId}`: Receive new messages for a specific chat.
*   `/user/topic/notifications`: Receive notifications for the current user.

## Authentication

Authentication is handled using Keycloak. The backend API expects a JSON Web Token (JWT) in the `Authorization` header of each request. The token is validated by the Keycloak server.

## Error Handling

The API uses a centralized exception handling mechanism to handle errors. When an error occurs, the API returns a JSON response with a descriptive error message and an appropriate HTTP status code.

## Database Migrations

Database migrations are managed using Flyway. The migration scripts are located in the `src/main/resources/db/migration` directory. Flyway automatically applies the migrations to the database when the application starts.

## API Documentation

The API is documented using OpenAPI. Once the application is running, you can access the OpenAPI documentation at `http://localhost:8080/swagger-ui.html`.
