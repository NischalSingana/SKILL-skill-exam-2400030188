# Stage 1: Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copy pom.xml and source files
COPY pom.xml .
COPY src ./src

# Build JAR file without running tests
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy built JAR from build stage with correct ownership
COPY --from=build --chown=spring:spring /app/target/*.jar app.jar

# Expose Spring Boot port
EXPOSE 8001

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]

