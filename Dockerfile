FROM maven:3.9.7-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package

FROM eclipse-temurin:21-jre-alpine

COPY --from=builder /app/target/LOLCODE-Interpreter-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
