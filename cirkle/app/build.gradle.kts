plugins {
    id("application.spring.conventions")
}

dependencies {
    implementation(project(":cirkle:api:rest"))
    implementation(project(":cirkle:api:graphql"))

    implementation("org.springframework.boot:spring-boot-starter")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation(project(":cirkle:test"))
}
