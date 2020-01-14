FROM java:8
EXPOSE 8080
ADD /target/usermanagement-0.0.1-SNAPSHOT.jar usermanagement.jar
ENTRYPOINT ["java","-jar","usermanagement.jar"]
