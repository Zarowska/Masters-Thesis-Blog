plugins {
    id("module.spring.conventions")
}

dependencies {
    api(project(":cirkle:domain:decl"))
    api(project(":cirkle:domain:persistence"))
    implementation("org.ehcache:ehcache")
    implementation("com.auth0:java-jwt:4.4.0")
}
