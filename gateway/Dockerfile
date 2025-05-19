FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/gateway-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
EXPOSE 8080
CMD ["/wait-for-it.sh", "eureka:8761", "--", "java", "-jar", "app.jar"]
