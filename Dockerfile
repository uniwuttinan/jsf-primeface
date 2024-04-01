# Stage 1: Build JSF application with Maven
FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src src
RUN mvn package

# Stage 2: Deploy the built artifact to Tomcat
FROM tomcat:9
COPY --from=build /app/target/my-jsf-app.war /usr/local/tomcat/webapps/
EXPOSE 8080
