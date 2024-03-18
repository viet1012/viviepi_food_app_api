FROM openjdk:19-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ./target/Viviepi-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","/app.jar"]