FROM openjdk:11
VOLUME /tmp
ADD ./target/account-service-0.0.1-SNAPSHOT.jar account-service.jar
ENTRYPOINT ["java", "-jar","./account-service.jar"]