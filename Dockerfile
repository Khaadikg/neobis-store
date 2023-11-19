FROM openjdk:17
MAINTAINER Khaadi Bolotbekov
COPY target/online-store-v.0.1.jar online-store.jar
ENTRYPOINT ["java","-jar","/online-store.jar"]