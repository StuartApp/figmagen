plugins {
    `maven-publish`
}

extensions.findByType<JavaPluginExtension>()?.apply {
    withSourcesJar()
    withJavadocJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }

        withType<MavenPublication>().configureEach {
            pom {
                name.set(property("pom.name").toString())
                description.set(property("pom.description").toString())
                url.set(property("pom.url").toString())

                licenses {
                    license {
                        name.set(property("pom.license.name").toString())
                        url.set(property("pom.license.url").toString())
                    }
                }

                developers {
                    developer {
                        id.set(property("pom.developer.id").toString())
                        name.set(property("pom.developer.name").toString())
                        email.set(property("pom.developer.email").toString())
                    }
                }

                scm {
                    url.set(property("pom.scm.url").toString())
                    connection.set(property("pom.scm.connection").toString())
                    developerConnection.set(property("pom.scm.developerConnection").toString())
                }
            }
        }
    }
}
