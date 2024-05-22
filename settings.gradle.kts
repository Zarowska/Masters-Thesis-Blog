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
    "cirkle:core",
    "cirkle:api:rest",
    "cirkle:api:rest-client",
    "cirkle:api:graphql",
    "cirkle:domain:decl",
    "cirkle:domain:impl",
    "cirkle:domain:persistence",
    "cirkle:test",
    "cirkle:app",
)
