plugins {
    id("module.spring.conventions")
}

dependencies {
    api(project(":cirkle:core"))
    api(project(":cirkle:domain:decl"))
    api("com.squareup.retrofit2:retrofit:2.11.0")
    api("com.squareup.retrofit2:converter-gson:2.11.0")
    api("com.google.code.gson:gson:2.10")
    api("org.apache.tika:tika-core:2.9.2")
    implementation("org.springframework:spring-web")
}
