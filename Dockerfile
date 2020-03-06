FROM openjdk:8
VOLUME /tmp
ADD target/club-management-0.0.1-SNAPSHOT.jar /app.jar
ENV TZ Asia/Shanghai
RUN bash -c 'touch /app.jar'
EXPOSE 8088

ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom  $JAVA_OPTS -jar /app.jar $RUN_ARGS