FROM maven:eclipse-temurin AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B -Dmaven.wagon.http.retryHandler.count=5 -Dmaven.wagon.http.connectionManagerTimeout=30000
COPY src ./src
RUN mvn clean package

FROM alpine/java:22-jdk
WORKDIR /app
COPY --from=build /app/target/task_time_tracker-1.0.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
