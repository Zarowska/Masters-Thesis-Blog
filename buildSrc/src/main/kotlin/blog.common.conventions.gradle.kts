plugins {
    id("com.diffplug.spotless")
}

repositories {
    mavenCentral()
}

dependencies {

}

val projectVersion: String by project

version = projectVersion

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}