plugins {
    id("application.spring.conventions")
}

dependencies {
    implementation(project(":cirkle:api:rest"))
    implementation(project(":cirkle:api:graphql"))
    implementation(project(":cirkle:domain:impl"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    implementation("io.micrometer:micrometer-registry-prometheus")

    runtimeOnly("org.postgresql:postgresql")

    testImplementation(project(":cirkle:test"))
    testImplementation(project(":cirkle:api:rest-client"))
}

tasks.bootJar {
    archiveFileName.set("cirkle-app.jar")
}

tasks.register("printVersion") {
    doLast {
        println("##teamcity[setParameter name='env.PROJECT_VERSION' value='${project.version}']")
    }
}
