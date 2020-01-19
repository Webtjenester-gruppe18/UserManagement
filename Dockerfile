FROM java:8
ADD /target/usermanagement-0.0.1-SNAPSHOT.jar usermanagement.jar
ENTRYPOINT ["java","-jar","usermanagement.jar"]
