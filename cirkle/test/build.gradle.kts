plugins {
    id("module.spring.conventions")
}

dependencies {
    implementation(project(":cirkle:core"))
    api("org.testcontainers:testcontainers")
    api("org.testcontainers:postgresql")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-test")
    api("org.springframework.boot:spring-boot-testcontainers")
    api("org.springframework.security:spring-security-test")
    runtimeOnly("org.postgresql:postgresql")
}
