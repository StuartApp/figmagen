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