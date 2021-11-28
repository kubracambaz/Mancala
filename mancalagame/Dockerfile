########Maven build stage########
FROM maven:3.8.1-openjdk-11-slim as maven_build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN --mount=type=cache,target=/root/.m2  mvn clean package -Dmaven.test.skip
RUN mkdir -p target/docker-packaging && cd target/docker-packaging && jar -xf ../mancalagame-0.0.1-SNAPSHOT.jar

########JRE run stage########
FROM openjdk:11.0-jre
WORKDIR /app
ARG DOCKER_PACKAGING_DIR=/app/target/docker-packaging
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/lib /app/lib
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/BOOT-INF/classes /app/classes
COPY --from=maven_build ${DOCKER_PACKAGING_DIR}/META-INF /app/META-INF

ENTRYPOINT ["java","-jar","/mancalagame.jar"]

