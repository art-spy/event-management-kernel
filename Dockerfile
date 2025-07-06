# base: OpenJDK 17 Slim
FROM openjdk:17-jdk-slim

# copy JAR into image
ARG JAR_FILE=target/kernel-1.0.0.jar
COPY ${JAR_FILE} app.jar

# expose port
EXPOSE 8181

# start command
ENTRYPOINT ["java","-jar","/app.jar"]
