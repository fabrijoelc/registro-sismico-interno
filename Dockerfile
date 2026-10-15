FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B verify
FROM tomcat:11.0-jdk21-temurin
COPY --from=build /build/target/sismos-pa2.war /usr/local/tomcat/webapps/sismos-pa2.war
EXPOSE 8080
