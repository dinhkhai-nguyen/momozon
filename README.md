# My First Project

This project contains:

- `commons`: shared Java domain classes
- `server`: Spring Boot backend
- `web`: React frontend built with Vite

## Requirements

- Java 25
- Node.js 24
- Git

Maven does not need to be installed globally because the project includes the Maven wrapper.

## Run the backend

From the project root:

```bash
./mvnw -pl server -am spring-boot:run