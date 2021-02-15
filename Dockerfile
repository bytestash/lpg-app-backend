FROM adoptopenjdk:11-jre-hotspot-bionic

EXPOSE 8080

ADD target/*.jar app.jar

ENV TZ=Europe/London

COPY target/classes/db/categories.csv /tmp/categories.csv
COPY target/classes/db/products.csv /tmp/products.csv

CMD java -jar app.jar