# This Dockerfile is used to build and run a Java application using Maven and OpenJDK 17.

# Use maven:3.8.4-openjdk-17 as a base image for the build stage
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory in the builder container to /app
WORKDIR /app

# Copy the project's pom.xml file into the builder container
COPY pom.xml .

# Copy the project's source code into the builder container
COPY src src

# List the contents of the current directory for debugging purposes
RUN ls -al

# Run the Maven package command to build the application
RUN mvn package

# Use eclipse-temurin:17 as a base image for the final stage
FROM eclipse-temurin:17

# Set the working directory in the final container to /app
WORKDIR /app

# Copy the built .jar file from the builder container to the final container
COPY --from=builder /app/target/*.jar /app/app.jar

# Set the command to run the application when the container starts
CMD ["java", "-jar", "app.jar"]