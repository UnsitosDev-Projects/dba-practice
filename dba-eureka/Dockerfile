# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /build

# Copy parent pom
COPY pom.xml /build/pom.xml

# Copy module pom and structure
COPY dba-eureka/pom.xml /build/dba-eureka/pom.xml

# Download dependencies
WORKDIR /build/dba-eureka
RUN mvn dependency:go-offline -B || true

# Copy source code
COPY dba-eureka/src /build/dba-eureka/src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /build/dba-eureka/target/*.jar app.jar

# Expose port
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
