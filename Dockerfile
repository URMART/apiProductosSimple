FROM openjdk:11
ARG JAR_FILE=target/productosApp-Test-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]