#!/bin/bash

# Step 1: Build the application JAR
echo "Building the application JAR..."
mvn clean package

# Step 2: Build the Docker image
echo "Building the Docker image..."
docker build -t myapplication:latest .

# Step 3: Run the Docker container
echo "Running the Docker container..."
docker run -d -p 8080:8080 --name myapp myapplication:latest

# Optional: Wait for the application to start
echo "Waiting for the application to start..."
sleep 10 # Adjust the sleep time as necessary

# Step 4: Test the application
echo "Testing the application..."
curl http://localhost:8080/

# Optional: Clean up
# Uncomment the following lines if you want to stop and remove the container after testing
# echo "Cleaning up..."
# docker stop myapp
# docker rm myapp