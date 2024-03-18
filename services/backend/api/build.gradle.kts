import plugin.AddLombokConstructorProcessor
import plugin.ModelsPreProcessorConfig
import java.time.Instant

plugins {
    id("blog.java.module.conventions")
    id("org.openapi.generator")
    id("com.opencastsoftware.gradle.buildinfo")
}

apply<plugin.ModelsPreProcessorPlugin>()

val generatedSourcesDir = "${layout.buildDirectory.asFile.get().absolutePath}/generated/openapi"

sourceSets {
    getByName("main") {
        java {
            srcDir("$generatedSourcesDir/src/main/java")
        }
    }
}

tasks.named("generateBuildInfo") {
    enabled = true
}

buildInfo {
    packageName.set("com.zarowska.cirkle")
    className.set("BuildInfo")
    properties.set(
        mapOf(
            "VERSION" to project.version.toString(),
            "BUILD_NUM" to (System.getenv("BUILD_NUM") ?: "local"),
            "BUILD_DATE" to Instant.now().toString(),
        ),
    )
}

dependencies {
    val springdocVersion: String by project

    api(project(":services:backend:shared"))
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("org.springframework.data:spring-data-commons")
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("jakarta.servlet:jakarta.servlet-api")
    implementation("org.hibernate.validator:hibernate-validator")
    implementation("org.openapitools:jackson-databind-nullable:0.2.6")
}

openApiGenerate {
    inputSpec = "${layout.projectDirectory.asFile.absolutePath}/src/main/resources/blog-rest-api.yaml"
    generatorName = "spring"
    apiPackage = "com.zarowska.cirkle.api.rest"
    modelPackage = "com.zarowska.cirkle.api.model"
    outputDir = generatedSourcesDir
    configOptions =
        mapOf(
            "useTags" to "true",
            "useSpringBoot3" to "true",
            "delegatePattern" to "true",
            "serializableModel" to "true",
            "useBeanValidation" to "true",
            "performBeanValidation" to "true",
        )
    importMappings =
        mapOf(
            "ProblemDetail" to "org.springframework.http.ProblemDetail",
            "ImageBody" to "org.springframework.core.io.Resource",
            "PageableObject" to "org.springframework.data.domain.Pageable",
            "SortObject" to "org.springframework.data.domain.Sort",
        )
    typeMappings =
        mapOf(
            "ProblemDetail" to "org.springframework.http.ProblemDetail",
            "ImageBody" to "org.springframework.core.io.Resource",
            "PageableObject" to "org.springframework.data.domain.Pageable",
            "SortObject" to "org.springframework.data.domain.Sort",
        )
}

configure<ModelsPreProcessorConfig> {
    srcDir = "$generatedSourcesDir/src/main/java"
    packageNames = setOf("com.zarowska.cirkle.api.model")
    processor = AddLombokConstructorProcessor()
}

tasks.named("modelsPreProcess") {
    dependsOn(
        tasks.named("openApiGenerate"),
    )
}

tasks.named("compileJava") {
    dependsOn(
        tasks.named("modelsPreProcess"),
    )
}
