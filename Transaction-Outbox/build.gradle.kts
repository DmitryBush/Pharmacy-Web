plugins {
    id("java")
    id("org.springframework.boot") version "3.4.3" apply false
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.bush"
version = "0.0.1-SNAPSHOT"

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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("org.mapstruct:mapstruct:1.6.3")
    compileOnly("org.mapstruct:mapstruct-processor:1.6.3")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

    implementation(":UUID-Adapter")
}

tasks.test {
    useJUnitPlatform()
}