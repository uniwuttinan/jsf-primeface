# Stage 1: Build JSF application with Maven
FROM maven:3.8-openjdk-8 AS build
WORKDIR /app
COPY pom.xml .
# RUN mvn dependency:go-offline
COPY src src
# RUN mvn package
RUN mvn install

# Stage 2: Deploy the built artifact to Tomcat
FROM tomcat:9-jdk8-openjdk
COPY --from=build /app/target/jsf-primefaces-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# possible database types:
# - MYSQL (must specify database connection settings)
# - HSQLDB_IN_MEMORY
# - HSQLDB_TO_FILE
# - SQLITE_TO_FILE
# - SQLITE_IN_MEMORY (default if not specified)
ENV DB_TYPE=SQLITE_IN_MEMORY

# database connection settings
ENV DB_NAME=""
ENV DB_HOST=""
ENV DB_USER=""
ENV DB_PASSWORD=""

EXPOSE 8080

#CMD ["catalina.sh", "run"]
