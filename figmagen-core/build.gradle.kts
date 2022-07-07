plugins {
    alias(libs.plugins.hubdle)
}

hubdle {
    config {
        explicitApi()
        publishing()
    }

    kotlin {
        jvm {
            features {
                coroutines()
            }
            main {
                dependencies {
                    implementation(libs.moshi)
                    implementation(libs.moshi.adapters)
                    implementation(libs.moshi.kotlin)
                    implementation(libs.okhttp)
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
