# Pulling the jdk 17 base image
FROM openjdk:17-jdk-slim

LABEL authors="Pragyanshu Rai"

WORKDIR /app

#Copying app jar file to the workdir jar file
COPY target/tinyurl-0.0.1-SNAPSHOT.jar TinyUrl.jar

# Exposing the 8080 port
EXPOSE 8080

# Running the jar file
CMD ["java", "-jar", "TinyUrl.jar"]