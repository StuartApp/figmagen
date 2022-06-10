plugins {
    alias(libs.plugins.kotlin.jvm)
    publish
}

kotlin {
    explicitApi()

    sourceSets.all {
        languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
    }
}

dependencies {
    api(libs.kotlinPoet)
    api(projects.figmagenCore)

    implementation(libs.coroutines.core)

    testImplementation(libs.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(projects.figmagenTest)
}
