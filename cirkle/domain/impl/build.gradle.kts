plugins {
    id("module.spring.conventions")
}

dependencies {
    api(project(":cirkle:domain:decl"))
    api(project(":cirkle:domain:persistence"))
}