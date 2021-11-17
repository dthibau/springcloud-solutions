FROM openjdk:11-jre-slim
LABEL David THIBAU <david.thibau@gmail.com>

ADD ./target/annuaire*.jar /app.jar
CMD ["java", "-Xmx200m", "-jar", "/app.jar"]

EXPOSE 1111