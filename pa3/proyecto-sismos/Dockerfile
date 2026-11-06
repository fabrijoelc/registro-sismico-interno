FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
COPY datos ./datos
RUN mvn -B verify

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd --system sismos && useradd --system --gid sismos sismos
COPY --from=build --chown=sismos:sismos /build/target/sismos-pa3.jar /app/sismos.jar
USER sismos
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/sismos.jar"]
