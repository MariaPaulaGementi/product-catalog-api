FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/catalogo.jar catalogo.jar
ENTRYPOINT ["java","-jar","catalogo.jar"]