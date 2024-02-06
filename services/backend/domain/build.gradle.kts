plugins {
    id("blog.java.module.conventions")
}

dependencies {
    val ehcacheVersion: String by project
    val tikaVersion: String by project

    api(project(":services:backend:shared"))

    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-validation")
    api("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("org.postgresql:postgresql")
    implementation("org.ehcache:ehcache:$ehcacheVersion")
    implementation("org.apache.tika:tika-core:$tikaVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
}
