# https://docs.docker.com/build/building/multi-stage/#use-multi-stage-builds

# First Stage
FROM azul/zulu-openjdk:21-latest AS builder
WORKDIR /src
COPY src ./src/
COPY gradle ./gradle/
COPY build.gradle.kts ./
COPY gradlew ./
COPY settings.gradle.kts ./
RUN chmod 777 ./gradlew
RUN ./gradlew clean build --no-daemon
RUN mkdir ./app \
    && cp ./build/libs/spring-boot-app-0.0.2-SNAPSHOT.jar  ./app/application.jar
RUN java --version
RUN java -Djarmode=tools -jar ./app/application.jar extract --layers --launcher
RUN echo $(ls -1 ./src)

# Second Stage
FROM azul/zulu-openjdk:21-latest
EXPOSE 8080
COPY --from=builder /src/application/dependencies/ ./
COPY --from=builder /src/application/snapshot-dependencies/ ./
COPY --from=builder /src/application/spring-boot-loader/ ./
COPY --from=builder /src/application/application/ ./
ENTRYPOINT ["java", "-Duser.timezone=GMT+1", "org.springframework.boot.loader.launch.JarLauncher"]