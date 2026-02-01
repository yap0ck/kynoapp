FROM maven:4.0.0-rc-4-amazoncorretto-25 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install

FROM amazoncorretto:25
ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} KynoAPP.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","KynoApp.jar"]