# Stage 1: Build JSF application with Maven
FROM maven:3.8-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
# RUN mvn dependency:go-offline
COPY src src
RUN mvn package

# Stage 2: Deploy the built artifact to Tomcat
FROM tomcat:8-jdk8-openjdk
COPY --from=build /app/target/jsf-primefaces-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/
EXPOSE 8080

CMD ["catalina.sh", "run"]