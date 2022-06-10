plugins {
    alias(libs.plugins.kotlin.jvm)
    publish
}

kotlin {
    explicitApi()
}

dependencies {
    api(projects.figmagenCore)
}
