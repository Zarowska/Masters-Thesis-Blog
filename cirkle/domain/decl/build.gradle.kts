plugins {
    id("module.spring.conventions")
}

dependencies {
    api(project(":cirkle:core"))
    api("org.springframework.security:spring-security-core")
}
