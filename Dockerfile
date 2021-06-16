# Start with a base image containing Java runtime
#FROM openjdk:11.0.6-stretch
#FROM openjdk:12-jdk-alpine
FROM openjdk:12-jdk-oraclelinux7
# Add Maintainer Info
LABEL maintainer="gabriele.deluca@eng.it"

#Install whois service
RUN yum install -y whois

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 9552 available to the world outside this container
EXPOSE 9552

# The application's jar file
ARG JAR_FILE=target/true-connector-uc_data_app-0.0.2-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} ids-usage-control-app.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/ids-usage-control-app.jar"]
#Healthy Status
HEALTHCHECK --interval=5s --retries=12 --timeout=10s \
  #CMD wget -O /dev/null http://localhost:9552/swagger-ui.html || exit 1
  CMD curl --fail http://localhost:9555/actuator/health  || exit 1

