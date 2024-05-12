FROM amazoncorretto:17-alpine3.19-jdk
WORKDIR /app
ENV PORT 8080
EXPOSE 8080
COPY build/libs/cirkle-app.jar /app/cirkle-app.jar
CMD ["java", "-jar", "/app/cirkle-app.jar"]