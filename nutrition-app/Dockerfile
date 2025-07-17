FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/nutrition-app.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

ENV TZ=Europe/Bucharest
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
