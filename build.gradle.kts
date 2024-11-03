plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.hibernate.orm") version "6.6.1.Final"
    id("com.diffplug.spotless") version "7.0.0.BETA3"
}

group = "blog.cirkle"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

extra["springAiVersion"] = "1.0.0-M3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-graphql")

    testImplementation("dev.langchain4j:langchain4j:0.35.0")
    testImplementation("dev.langchain4j:langchain4j-mistral-ai:0.35.0")
    testImplementation("net.datafaker:datafaker:2.4.0")
    testImplementation("com.google.guava:guava:33.3.1-jre")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.auth0:java-jwt:4.4.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
    implementation("org.apache.tika:tika-core:2.9.2")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    compileOnly("org.projectlombok:lombok")
    implementation("org.springframework.ai:spring-ai-mistral-ai-spring-boot-starter")
    testCompileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testAnnotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.graphql:spring-graphql-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("com.squareup.retrofit2:retrofit:2.11.0")
    testImplementation("com.squareup.retrofit2:converter-gson:2.11.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

hibernate {
    enhancement {
        enableAssociationManagement = true
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

spotless {
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
    java {
        targetExclude("*/generated/**")
        cleanthat()
            .addMutator("SafeAndConsensual")

        importOrder()
        removeUnusedImports()
        eclipse()
        formatAnnotations()
    }
}
