FROM openjdk:11
VOLUME /tmp
ADD ./target/transaction-service-0.0.1-SNAPSHOT.jar transaction-service.jar
ENTRYPOINT ["java", "-jar","./transaction-service.jar"]