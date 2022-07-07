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
            main {
                dependencies {
                    api(projects.figmagenCore)
                }
            }
        }
    }
}
