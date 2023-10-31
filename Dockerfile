FROM openjdk:17-alpine

EXPOSE 8080

ADD target/Dot_ai.jar Dot_ai.jar

ENTRYPOINT ["java","-jar","Dot_ai.jar"]