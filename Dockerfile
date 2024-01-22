FROM openjdk:17

VOLUME /tmp

ARG JAR_FILE=build/libs/challenge-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} challenge-service.jar

ENTRYPOINT ["java", "-jar","/challenge-service.jar"]