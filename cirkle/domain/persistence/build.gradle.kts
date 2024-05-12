plugins {
    id("module.spring.conventions")
}

dependencies {
    api(project(":cirkle:core"))
    api(project(":cirkle:domain:decl"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.flywaydb:flyway-core")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("com.github.slugify:slugify:3.0.6") { // todo move to properties
        capabilities {
            requireCapability("com.github.slugify:slugify-transliterator")
        }
    }
}
