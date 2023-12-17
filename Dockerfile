FROM openjdk:17
ADD target/vote-machine-v1.jar vote-machine-v1.jar
ENTRYPOINT ["java","-jar","/vote-machine-v1.jar"]