FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/ids-usage-control-app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 9552