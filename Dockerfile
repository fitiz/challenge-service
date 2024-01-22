FROM openjdk:17

ENV APP_NAME challenge-service
ENV CHALLENGE_SERVICE_API_PORT 9000

ARG JAR_FILE=build/libs/challenge-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} challenge-service.jar

EXPOSE ${CHALLENGE_SERVICE_API_PORT}

ENTRYPOINT ["java", "-jar","challenge-service.jar"]