plugins {
    id("spring.conventions")
    id("org.springframework.boot") apply true
    application
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

