plugins {
    id("application.spring.conventions")
}

dependencies {
    implementation(project(":cirkle:api:rest"))
    implementation(project(":cirkle:api:graphql"))

    implementation(project(":cirkle:domain:persistence")) // todo make transitive through domain:impl

    implementation("org.springframework.boot:spring-boot-starter")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation(project(":cirkle:test"))
}
