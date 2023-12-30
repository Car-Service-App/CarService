FROM eclipse-temurin:17-alpine
WORKDIR .
COPY /target/CarService-0.0.1-SNAPSHOT.jar CarService.jar

ENTRYPOINT ["java","-jar","CarService.jar"]