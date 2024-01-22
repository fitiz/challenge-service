# Base image
FROM openjdk:17

# Environment variables
ENV APP_NAME challenge-service
ENV CHALLENGE_SERVICE_API_PORT 9000

# Build the JAR file
RUN ./gradlew build

# Copy the JAR file to the container
COPY build/libs/challenge-service-0.0.1-SNAPSHOT.jar challenge-service.jar

# Expose the port
EXPOSE 9000

# Start the application
ENTRYPOINT ["java", "-jar", "challenge-service.jar"]