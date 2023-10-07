FROM gradle:8.4.0-jdk17-alpine AS BUILDER

WORKDIR /build

COPY candidate-service/src candidate-service/src

COPY settings.gradle.kts settings.gradle.kts

COPY candidate-service/build.gradle.kts candidate-service/build.gradle.kts

COPY gradle/plugins gradle/plugins

RUN gradle clean assemble

#------------------------------------------------------

FROM bellsoft/liberica-openjdk-alpine:17.0.8

WORKDIR /app

COPY --from=BUILDER /build/candidate-service/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]