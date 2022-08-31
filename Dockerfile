FROM openjdk:11-jdk-alpine
VOLUME /tmp
ARG JAR_FILE= target/* .jar
COPY ${JAR_FILE} student-service.jar
ENTRYPOINT ["java","-jar","/student-service.jar"]