plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.hibernate.orm") version "6.6.1.Final"
    id("com.diffplug.spotless") version "7.0.0.BETA3"
    id("io.gatling.gradle") version "3.13.3"
    id("com.github.ben-manes.versions") version "0.51.0"
    id("scala")
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

dependencies {
    val langchain4j = "0.36.2"
    implementation("dev.langchain4j:langchain4j:$langchain4j")
    implementation("dev.langchain4j:langchain4j-core:$langchain4j")
    implementation("dev.langchain4j:langchain4j-mistral-ai:$langchain4j")
    implementation("dev.langchain4j:langchain4j-open-ai:$langchain4j")
    implementation("dev.langchain4j:langchain4j-local-ai:$langchain4j")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-docker-compose")

    implementation("io.gatling:gatling-core:3.13.3")
    implementation("io.gatling:gatling-http:3.13.3")
    implementation("org.scala-lang:scala-library:2.13.11")
    implementation("org.scala-lang:scala-reflect:2.13.11")
    implementation("org.scala-lang:scala-compiler:2.13.11")

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
