FROM openjdk:11
ARG JAR_FILE
COPY ${JAR_FILE} student-service.jar
ENTRYPOINT ["java","-jar","/student-service.jar"]