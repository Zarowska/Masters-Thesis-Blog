plugins {
    id("blog.java.common.conventions")
    `java-library`
}

tasks.named("bootJar") {
    enabled = false
}
tasks.named("bootRun") {
    enabled = false
}
tasks.named("bootTestRun") {
    enabled = false
}