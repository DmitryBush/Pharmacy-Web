plugins {
    id("java")
    id("org.springframework.boot") version "3.4.3" apply false
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.bush"
version = "0.0.1-SNAPSHOT"
description = "UUID hibernate adapters for use in modules"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.9")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot")
    implementation("com.fasterxml.uuid:java-uuid-generator:5.2.0")
    implementation("org.hibernate.orm:hibernate-core:6.6.40.Final")
}

tasks.test {
    useJUnitPlatform()
}