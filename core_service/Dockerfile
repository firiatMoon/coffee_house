FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/core_service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} core_service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/core_service.jar"]

