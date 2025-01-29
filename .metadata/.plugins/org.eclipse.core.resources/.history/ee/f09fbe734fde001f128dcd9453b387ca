FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/apartment-service-0.0.1-SNAPSHOT.jar app.jar
COPY apartments.db /app/
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "app.jar"]
