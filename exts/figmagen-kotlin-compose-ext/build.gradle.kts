plugins {
    alias(libs.plugins.hubdle)
}

hubdle {
    config {
        explicitApi()
        languageSettings {
            experimentalCoroutinesApi()
        }
        publishing()
    }

    kotlin {
        jvm {
            features {
                coroutines()
            }
            main {
                dependencies {
                    api(projects.figmagenCore)
                }
            }
            test {
                dependencies {
                    implementation(projects.figmagenTest)
                }
            }
        }
    }
}
