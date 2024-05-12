import java.util.Properties

plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

Properties().apply {
    val props = this
    rootDir.toPath().resolveSibling(Project.GRADLE_PROPERTIES).toFile().apply {
        val file = this
        file.inputStream().use {
            props.load(it)
        }
    }
}.forEach { key, value -> project.extra.set(key as String, value) }

dependencies {
    val dependencyManagementPluginVersion: String by project
    val springBootPluginVersion: String by project
    val lombokPluginVersion: String by project
    val spotlessPluginVersion: String by project

    implementation("io.spring.gradle:dependency-management-plugin:$dependencyManagementPluginVersion")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$springBootPluginVersion")
    implementation("io.freefair.gradle:lombok-plugin:$lombokPluginVersion")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:$spotlessPluginVersion")
    implementation("com.opencastsoftware.gradle:gradle-build-info:0.3.1") // breaks debug? todo move to properties
    implementation("org.hibernate.orm:hibernate-gradle-plugin:6.5.0.Final") // todo move to properties
}
