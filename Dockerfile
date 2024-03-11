# Use OpenJDK 17 as the base image
FROM openjdk:17

# Copy the built JAR file from your Gradle build output directory into the Docker image
COPY build/libs/*.jar app.jar

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app.jar"]
