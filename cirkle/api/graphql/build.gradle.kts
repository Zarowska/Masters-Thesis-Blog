plugins {
    id("module.spring.conventions")
}

dependencies {
    api(project(":cirkle:core"))
    api(project(":cirkle:domain:decl"))
}