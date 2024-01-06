#!/bin/bash
set -xe


# Copy war file from S3 bucket to tomcat webapp folder
aws s3 cp s3://codedeploystack-webappdeploymentbucket-hre3kakybzrj/challenge-service-0.0.1-SNAPSHOT.war /usr/local/tomcat9/webapps/challenge-service-0.0.1-SNAPSHOT.war


# Ensure the ownership permissions are correct.
chown -R tomcat:tomcat /usr/local/tomcat9/webapps