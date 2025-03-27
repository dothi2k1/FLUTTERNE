FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copy source code and build the application
COPY . . 

# Grant execute permission to mvnw
RUN chmod +x mvnw 

# Run Maven build
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8065

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
