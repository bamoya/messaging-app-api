# Stage 1: Build the application with Maven
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Create a slim, production-ready image
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/whatsapp-clone-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

# This passes a command-line argument to the Spring Boot application.
ENTRYPOINT ["java", "-jar", "whatsapp-clone-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]