plugins {
    id("blog.java.module.conventions")
}

dependencies {
    api(project(":services:backend:api"))
    compileOnly("io.swagger.core.v3:swagger-annotations:2.2.19")
}
