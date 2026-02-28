FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/coffee_house-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} coffee_house.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/coffee_house.jar"]

