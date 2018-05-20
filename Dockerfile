FROM openjdk:8-jdk-alpine
CMD ["gradle"]
WORKDIR /usr/src/java-code
COPY . /usr/src/java-code/
RUN ./gradlew build

WORKDIR /usr/src/java-app
RUN cp /usr/src/java-code/build/libs/*.jar ./app.jar
EXPOSE 8090
CMD ["java", "-jar", "app.jar"]