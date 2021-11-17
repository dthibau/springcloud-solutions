FROM openjdk:11-jre-slim
MAINTAINER David THIBAU <david.thibau@gmail.com>

ADD ./target/orders-service.jar /app/order-service.jar
CMD ["java", "-Xmx200m", "-jar", "/app/order-service.jar"]

EXPOSE 2222
