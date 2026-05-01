# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin-21-jre-alpine
WORKDIR /app

# Security: Run as non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy artifact from build stage
COPY --from=build /app/target/fx-core-1.0.0.jar app.jar

# Port mapping
EXPOSE 8085

# Healthcheck: Ensures the container is actually ready to handle traffic
# Requires spring-boot-starter-actuator in your pom.xml
HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
  CMD wget -qO- http://localhost:8085/actuator/health | grep UP || exit 1

# Final Entrypoint (Fixed the closing quote)
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", \
            "app.jar"]