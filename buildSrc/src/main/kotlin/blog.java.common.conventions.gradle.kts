plugins {
    id("blog.common.conventions")
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    id("io.freefair.lombok")
    java
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

spotless {
    java {
        targetExclude("*/generated/**")
        importOrder()
        removeUnusedImports()
        cleanthat()
        eclipse()
        formatAnnotations()
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}