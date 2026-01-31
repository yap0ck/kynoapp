# syntax=docker/dockerfile:1

# -------- Build stage --------
FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

# Copy Maven wrapper + pom first (better layer caching)
COPY .mvn .mvn
COPY mvnw mvnw
COPY pom.xml pom.xml

# mvnw needs execute permission in Linux containers
RUN chmod +x mvnw

# Download dependencies (cacheable)
RUN ./mvnw -q -DskipTests dependency:go-offline

# Copy sources and build the Spring Boot jar
COPY src src
RUN ./mvnw -q -DskipTests clean package

# -------- Runtime stage --------
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy the built jar (Spring Boot Maven plugin produces a single runnable jar)
COPY --from=build /workspace/target/*.jar app.jar

# Spring Boot default port is 8080 (adjust if you use another one in prod)
EXPOSE 8080

# Optional: set default active profile (can still be overridden by env in compose)
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java","-jar","/app/app.jar"]