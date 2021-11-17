FROM openjdk:11-jre-slim
MAINTAINER David THIBAU <david.thibau@gmail.com>

ADD ./target/gateway*.jar /app.jar
CMD ["java", "-Xmx200m", "-jar", "/app.jar"]

EXPOSE 8000