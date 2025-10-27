# Stage 1: Build with Maven
FROM maven:3.9.11-eclipse-temurin-25 AS build

# Set working directory
WORKDIR /app

# Copy Maven project files
COPY pom.xml ./
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Stage 2: Run with JDK 25
FROM eclipse-temurin:25-jdk

# Set working directory
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port (adjust if needed)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
