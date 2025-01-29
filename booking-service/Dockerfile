
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/booking-service-0.0.1-SNAPSHOT.jar app.jar
COPY booking.db /app/
EXPOSE 8087
ENV SERVER_PORT=8087
ENTRYPOINT ["java", "-jar", "app.jar"]
