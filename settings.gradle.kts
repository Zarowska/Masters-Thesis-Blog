pluginManagement {
    includeBuild("build-logic")
}

buildCache {
    local {
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "cirkle-blog"
include(
    "circle:core",
    "circle:api:rest",
    "circle:api:graphql",
    "circle:api:domain:decl",
    "circle:api:domain:impl",
    "circle:api:domain:persistence",
    "circle:app",
)
