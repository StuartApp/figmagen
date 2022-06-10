enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(
    ":exts:figmagen-kotlin-compose-ext",
)

include(
    ":figmagen-core",
    ":figmagen-test",
)
