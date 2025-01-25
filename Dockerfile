FROM openjdk:17-alpine
WORKDIR /opt
ENV PORT=8092
EXPOSE 8092
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar