# ---------- BUILD STAGE ----------
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# First copy pom.xml and download deps (better caching)
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Then copy the source
COPY src ./src

# Build the Spring Boot jar
RUN mvn -q -DskipTests package

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/album-player-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Standard Spring Boot entrypoint
ENTRYPOINT ["java","-jar","/app/app.jar"]
