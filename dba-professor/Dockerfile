# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /build

# Copy parent pom
COPY pom.xml /build/pom.xml

# Copy module pom and structure
COPY dba-professor/pom.xml /build/dba-professor/pom.xml

# Download dependencies
WORKDIR /build/dba-professor
RUN mvn dependency:go-offline -B || true

# Copy source code
COPY dba-professor/src /build/dba-professor/src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /build/dba-professor/target/*.jar app.jar

# Expose port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
