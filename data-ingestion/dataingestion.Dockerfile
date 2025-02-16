FROM ubuntu:latest

RUN apt-get update && apt-get install libpcap-dev openjdk-21-jdk -y

WORKDIR /app

COPY  ./build/libs/data-ingestion-*.jar ./data-ingestion.jar

RUN chmod +x ./data-ingestion.jar

ENTRYPOINT ["java", "-jar", "data-ingestion.jar"]
