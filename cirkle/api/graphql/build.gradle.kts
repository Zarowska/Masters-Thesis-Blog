plugins {
    id("module.spring.conventions")
}

dependencies {
    implementation(project(":cirkle:core"))
    implementation(project(":cirkle:domain:decl"))
}
