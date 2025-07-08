# base: OpenJDK 17 Slim
FROM openjdk:17-jdk-slim

# copy JAR into image
ARG JAR_FILE=event-management-kernel/target/event-management-kernel-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# expose port
EXPOSE 8181

# start command
ENTRYPOINT ["java","-jar","/app.jar"]