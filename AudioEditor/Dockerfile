FROM ubuntu:latest as build

RUN apt-get update
RUN apt-get install -y openjdk-17-jdk

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .
COPY src src

RUN chmod +x mvnw

RUN ./mvnw package -DskipTests

FROM openjdk:17-jdk-slim

# Install ffmpeg in the final image
RUN apt-get update && \
    apt-get install -y ffmpeg && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
