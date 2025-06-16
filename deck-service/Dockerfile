FROM maven:3.9.8 AS builder
WORKDIR /app
COPY . .
RUN mvn clean formatter:format install

FROM openjdk:21
WORKDIR /app

COPY --from=builder /app/target/deck-service-1.0-SNAPSHOT.jar app.jar

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]

EXPOSE 1002