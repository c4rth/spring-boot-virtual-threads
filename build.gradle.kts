plugins {
    java
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.cyclonedx.bom") version "1.8.2"
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencyManagement {
    imports {
        mavenBom("io.opentelemetry.instrumentation:opentelemetry-instrumentation-bom:2.6.0")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3")
        //mavenBom("io.opentelemetry.instrumentation:opentelemetry-instrumentation-bom-alpha:2.2.0-alpha")
        mavenBom("com.azure.spring:spring-cloud-azure-dependencies:5.15.0")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    //implementation("io.opentelemetry.instrumentation:opentelemetry-spring-boot-starter")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
    implementation("org.springframework.cloud:spring-cloud-starter-kubernetes-fabric8-all")
    //runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")


    implementation("io.micrometer:micrometer-tracing")
    implementation("io.micrometer:micrometer-tracing-bridge-otel")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp")
    //implementation("io.opentelemetry:opentelemetry-exporter-zipkin")
    implementation("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("com.github.loki4j:loki-logback-appender:latest.release")

    /*

    implementation("org.springframework.hateoas:spring-hateoas")

    implementation("com.azure.spring:spring-cloud-azure-starter-actuator")
    implementation("com.azure.spring:spring-cloud-azure-starter-active-directory")
    implementation("com.azure.spring:spring-cloud-azure-starter-keyvault-secrets")

    implementation("com.h2database:h2")
    implementation("org.flywaydb:flyway-core")
    implementation("com.microsoft.sqlserver:mssql-jdbc")


    implementation("org.springframework.boot:spring-boot-starter-webflux")


    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    implementation("org.zalando:problem:0.27.1")
    implementation("org.zalando:problem-spring-web:0.29.1")
    implementation("org.zalando:problem-spring-webflux:0.29.1")


    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.mapstruct:mapstruct:1.5.5.Final")

    */

}

group = "org.c4rth"
version = "0.0.1-SNAPSHOT"
description = "spring-boot-virtual-threads-test"
