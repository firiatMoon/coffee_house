FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/tg_service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} tg_service.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "/tg_service.jar"]

