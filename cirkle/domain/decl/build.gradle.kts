plugins {
    id("module.spring.conventions")
}

dependencies {
    api(project(":cirkle:core"))
    api("org.springframework.security:spring-security-core")
    api("org.springframework.boot:spring-boot-starter-cache")
    api("org.springframework.data:spring-data-commons")
    api("org.springframework:spring-tx")
    api("org.springframework:spring-web")
    api("org.springframework.boot:spring-boot-starter-validation")
    compileOnly("jakarta.platform:jakarta.jakartaee-api:8.0.0") // todo ??
}
