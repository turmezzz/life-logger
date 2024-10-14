FROM --platform=linux/amd64 openjdk:20-slim
RUN mkdir /app
COPY ./build/libs/LifeLogger-all.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
