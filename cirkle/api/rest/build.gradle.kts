plugins {
    id("module.spring.conventions")
}

dependencies {
    api(project(":cirkle:core"))
    api(project(":cirkle:domain:decl"))
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-starter-security")
}
