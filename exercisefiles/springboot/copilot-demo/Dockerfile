# Build the Java application
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests
# Rename the JAR file to a fixed name for easier copying
RUN ls -l /app/target
RUN mv /app/target/*.jar /app/target/myapplication.jar

# Create a new image based on openjdk 17 and run the application
FROM openjdk:17-jdk-slim
LABEL maintainer="your_email@example.com"
WORKDIR /app
# Now copy the renamed JAR file
COPY --from=build /app/target/myapplication.jar /app/myapplication.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/myapplication.jar"]