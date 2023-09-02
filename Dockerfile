# ### All dockerized
# ## Stage 1: Build the JAR file
FROM openjdk:17-jdk-alpine as build

# Add the Maven dependency
RUN apk add --no-cache maven

# Set the working directory to /opt/app
WORKDIR /opt/app

# Copy the Spring Boot application source code to the working directory
COPY . .

# Build the JAR file
RUN mvn clean package -DskipTests



# ## Stage 2: Run the JAR file
FROM openjdk:17-jdk-alpine

# Copy the JAR file from the build stage
COPY --from=build /opt/app/target/*.jar /opt/app/app.jar

# Set the working directory to /opt/app
WORKDIR /opt/app

# Expose the default Spring Boot port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java","-jar","app.jar"]











# # ### All dockerized
# # Use the official Java 17 image as the base image
# FROM openjdk:17-jdk-alpine

# # Add the Maven dependency
# RUN apk add --no-cache maven

# # Set the working directory to /opt/app
# WORKDIR /opt/app

# # Copy the Spring Boot application source code to the working directory
# COPY . .

# # Create the target directory
# RUN mkdir -p target

# # Build the JAR file
# RUN mvn clean package -DskipTests

# # Expose the default Spring Boot port
# EXPOSE 8080

# # Run the Spring Boot application
# ENTRYPOINT ["java","-jar","target/inventory-management-0.0.1-SNAPSHOT.jar"]









# ### Requires prior creation of JAR
# FROM openjdk:17-jdk-alpine

# # change this based on the `mvn clean package -DskipTests` JAR result
# COPY target/inventory-management-0.0.1-SNAPSHOT.jar java-app.jar

# ENTRYPOINT ["java", "-jar", "java-app.jar"]

