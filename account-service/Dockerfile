FROM openjdk:11-jre-slim
MAINTAINER David THIBAU <david.thibau@gmail.com>

ADD ./target/account-service*.jar /app/account-service.jar
CMD ["java", "-Xmx200m", "-jar", "/app/account-service.jar"]

EXPOSE 8080
