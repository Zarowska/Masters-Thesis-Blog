
plugins {
    id("java.conventions")
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
    id("org.hibernate.orm")
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

tasks.withType<Test> {
    useJUnitPlatform()
}

hibernate {
    enhancement {
        enableAssociationManagement = true
    }
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



