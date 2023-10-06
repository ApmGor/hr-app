FROM gradle:8.4.0-jdk17-alpine AS BUILDER

WORKDIR /build

COPY job-service/src job-service/src

COPY settings.gradle.kts settings.gradle.kts

COPY job-service/build.gradle.kts job-service/build.gradle.kts

COPY gradle/plugins gradle/plugins

RUN gradle clean assemble

#------------------------------------------------------

FROM bellsoft/liberica-openjdk-alpine:17.0.8

WORKDIR /app

COPY --from=BUILDER /build/job-service/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]