# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /build

# Copy parent pom
COPY pom.xml /build/pom.xml

# Copy module pom and structure
COPY dba-gateway/pom.xml /build/dba-gateway/pom.xml

# Download dependencies
WORKDIR /build/dba-gateway
RUN mvn dependency:go-offline -B || true

# Copy source code
COPY dba-gateway/src /build/dba-gateway/src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /build/dba-gateway/target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
