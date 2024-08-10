# https://docs.docker.com/build/building/multi-stage/#use-multi-stage-builds

# First Stage
FROM azul/zulu-openjdk:21-latest AS builder
WORKDIR /src
COPY src ./src/
COPY opentelemetry-javaagent-2.6.0.jar ./
COPY gradle ./gradle/
COPY build.gradle.kts ./
COPY gradlew ./
COPY settings.gradle.kts ./
RUN chmod 777 ./gradlew
RUN ./gradlew clean build --no-daemon
RUN mkdir ./app \
    && cp ./build/libs/spring-boot-virtual-threads-test-0.0.1-SNAPSHOT.jar  ./app/application.jar
RUN java -Djarmode=tools -jar ./app/application.jar extract --layers --launcher
#ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.6.0/opentelemetry-javaagent.jar ./opentelemetry-agent.jar


# Second Stage
FROM azul/zulu-openjdk:21-latest
EXPOSE 8080
COPY --from=builder /src/application/dependencies/ ./
COPY --from=builder /src/application/snapshot-dependencies/ ./
COPY --from=builder /src/application/spring-boot-loader/ ./
COPY --from=builder /src/application/application/ ./
COPY --from=builder /src/opentelemetry-javaagent-2.6.0.jar ./
ENTRYPOINT ["java", "-javaagent:opentelemetry-javaagent-2.6.0.jar", "-Duser.timezone=GMT+1", "org.springframework.boot.loader.launch.JarLauncher"]
#ENTRYPOINT ["java", "-Duser.timezone=GMT+1", "org.springframework.boot.loader.launch.JarLauncher"]