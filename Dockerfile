FROM gradle:jdk21-alpine AS builder

WORKDIR /build
COPY Transaction-Outbox ./Transaction-Outbox
COPY UUID-Adapter ./UUID-Adapter
COPY src ./src
COPY build.gradle.kts ./
COPY settings.gradle.kts ./

RUN gradle build --no-daemon --parallel -x test

FROM amazoncorretto:21-alpine

WORKDIR /app
COPY --from=builder build/build/libs/*.jar pharmacy-web-backend-app.jar
ENTRYPOINT [ "java", "-jar", "pharmacy-web-backend-app.jar" ]