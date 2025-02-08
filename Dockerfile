# Stage 1: Build the app using Gradle (skip tests)
FROM amazoncorretto:21 AS builder
WORKDIR /app
# Copy the entire project (adjust if needed)
COPY . .
# Ensure the gradlew script is executable and build the app, skipping tests
RUN chmod +x gradlew && ./gradlew build -x test

# Stage 2: Package the built app with Amazon Corretto 21 for runtime
FROM amazoncorretto:21
WORKDIR /app
# Copy the JAR file from the build stage (adjust the path if your jar is named differently)
COPY --from=builder /app/build/libs/*.jar app.jar
# Expose the port your Spring Boot app uses (default 8080)
EXPOSE 8080
# Run the app with the 'docker' profile (you can modify the profile as needed)
ENTRYPOINT ["java", "-jar", "app.jar"]