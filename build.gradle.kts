buildscript {
    dependencies {
        classpath(libs.kotlin.kotlinGradlePlugin)
    }
}

plugins {
    alias(libs.plugins.hubdle)
}

hubdle {
    config {
        analysis()
        binaryCompatibilityValidator()
        coverage()
        documentation {
            site()
        }
        nexus()
    }
}