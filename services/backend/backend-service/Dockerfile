FROM amazoncorretto:17-alpine
VOLUME /tmp
VOLUME /cache
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
# ,"--add-opens java.base/java.lang=ALL-UNNAMED" ??