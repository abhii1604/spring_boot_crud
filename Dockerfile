FROM jdk:17-jdk

COPY target/Book-0.0.1-SNAPSHOT.jar  .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Book-0.0.1-SNAPSHOT.jar"]