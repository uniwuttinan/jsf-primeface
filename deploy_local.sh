#!/bin/sh

# Define the path to the Tomcat server
TOMCAT_PATH=/home/codespace/.rsp/redhat-community-server-connector/runtimes/installations/tomcat-9.0.41/apache-tomcat-9.0.41

# Define the path to the .war file
WAR_FILE_PATH=/workspaces/jsf-primeface/target/jsf-primefaces-0.0.1-SNAPSHOT.war

# Define the name of the application as it will appear in Tomcat
APP_NAME=ROOT

# Remove any existing application with the same name
rm -rf $TOMCAT_PATH/webapps/$APP_NAME

# Remove any existing .war file with the same name
rm -f $TOMCAT_PATH/webapps/$APP_NAME.war

# Copy the new .war file to the Tomcat server
cp $WAR_FILE_PATH $TOMCAT_PATH/webapps/$APP_NAME.war

# Restart the Tomcat server
$TOMCAT_PATH/bin/shutdown.sh 2>/dev/null
$TOMCAT_PATH/bin/startup.sh

echo "Deployment completed"