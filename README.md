# Spring Boot 3.0 Security with JWT Implementation

This project demonstrates the implementation of security using Spring Boot 3.0 and JSON Web Tokens (JWT). It includes the following features:

## Features

⚡️ User registration and login with JWT authentication\
⚡️ Password encryption using BCrypt\
⚡️ Role-based authorization with Spring Security\
⚡️ Customized access denied handling

## Technologies

- Spring Boot 3.0.9
- Spring Security
- PostgreSQL
- JSON Web Tokens (JWT)
- BCrypt
- Maven

## Getting Started

To get started with this project, you will need to have the following installed on your local machine:

- JDK 8+
- Maven 3+

To build and run the project, follow these steps:

- Clone the repository: `https://github.com/AlexMartin998/spring-boot-3-jwt-skeleton.git`
- Build the project: `mvn clean install`
- Run the project: `mvn spring-boot:run`

### Running the DB

- Create the required volumes

```bash
docker volume create spring_mysql
```

- Create `.env` file based on the `.env.example` file

```bash
docker compose -f docker-compose.dev.yml up --build -d
```

-> The application will be available at http://localhost:3000
