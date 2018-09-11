FROM technekes/centos-java:oracle-8-jre
VOLUME /tmp
COPY target/identity-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]