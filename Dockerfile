FROM technekes/centos-java:oracle-8-jre
ENV LC_ALL=C
ARG JAR_FILE
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]