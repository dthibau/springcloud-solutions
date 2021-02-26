FROM openjdk:8-jre-alpine
MAINTAINER David THIBAU <david.thibau@gmail.com>

ADD ./target/deliver*SNAPSHOT.jar /app.jar
CMD ["java", "-Xmx200m", "-jar", "/app.jar"]

EXPOSE 3333
