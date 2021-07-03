FROM openjdk:11-jre-slim-buster

WORKDIR /app

ADD build/libs .

RUN find /app -name "*.jar" -not -name "*-plain.jar" -exec mv "{}" service.jar \;

ENTRYPOINT ["java", "-jar", "/app/service.jar"]