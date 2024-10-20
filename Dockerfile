FROM --platform=linux/amd64 openjdk:20-slim
ADD https://dl.google.com/cloudsql/cloud_sql_proxy.linux.amd64 /cloud_sql_proxy
RUN chmod +x /cloud_sql_proxy
RUN mkdir /app
COPY ./build/libs/LifeLogger-all.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c", "/cloud_sql_proxy -dir=/cloudsql -instances=life-logger-3:europe-west1:turmetsmakoev-db=tcp:5432 & java -jar /app/app.jar"]
