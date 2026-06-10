FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/PriceHistory-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]