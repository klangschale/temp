FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/webserver-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
EXPOSE 8083
CMD ["/wait-for-it.sh", "gateway:8080", "--", "java", "-jar", "app.jar"]
