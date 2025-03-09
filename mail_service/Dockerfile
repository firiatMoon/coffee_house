FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/mail_service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} mail_service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/mail_service.jar"]

