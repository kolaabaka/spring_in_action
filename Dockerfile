#also u could use maven and spring boot instead cli docker, but u never saw creating image like this

FROM eclipse-temurin:17-jre-alpine

RUN mkdir app
WORKDIR app

EXPOSE 8080

COPY ./target/*.jar ./app.jar

ENTRYPOINT ["java", "-jar"]
CMD ["app.jar"]