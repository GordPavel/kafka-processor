FROM openjdk:11-jre-slim-buster
COPY ./build/libs/*.jar ./application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","application.jar"]