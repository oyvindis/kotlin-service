FROM eclipse-temurin:17-jre-alpine

ENV TZ=Europe/Oslo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY /target/app.jar app.jar

CMD java -jar $JAVA_OPTS app.jar