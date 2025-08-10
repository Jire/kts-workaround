import org.jreleaser.model.Active
import org.jreleaser.model.Signing

plugins {
    kotlin("jvm")
    `maven-publish`
    signing
    id("org.jreleaser")
}

group = "org.jire.ktsworkaround"
version = "1.0.0"
description = "Easily fix KTS editor support for new IntelliJ versions"

val isSnapshot = version.toString().endsWith("SNAPSHOT")

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("scripting-common"))
    implementation(kotlin("scripting-jvm"))
    implementation(kotlin("scripting-jvm-host"))
    implementation(kotlin("scripting-dependencies"))
    implementation(kotlin("scripting-dependencies-maven"))
}

kotlin {
    jvmToolchain(11)
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = if (isSnapshot) "staging" else "release"
            setUrl(layout.buildDirectory.dir(if (isSnapshot) "staging-deploy" else "release-deploy"))
        }
    }
    publications {
        create<MavenPublication>("script-def") {
            from(components["java"])

            pom {
                name = rootProject.name
                description = rootProject.description
                url = "https://github.com/Jire/ktsworkaround"
                packaging = "jar"
                licenses {
                    license {
                        name = "MIT License"
                        url = "https://github.com/Jire/ktsworkaround/blob/main/LICENSE.txt"
                    }
                }
                developers {
                    developer {
                        id = "Jire"
                        name = "Thomas Nappo"
                        email = "thomasgnappo@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/Jire/ktsworkaround.git"
                    developerConnection = "scm:git:ssh://git@github.com/Jire/ktsworkaround.git"
                    url = "https://github.com/Jire/ktsworkaround"
                }
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

jreleaser {
    project {
        author("Jire")
        license = "MIT"
        links {
            homepage = "https://github.com/Jire/ktsworkaround"
        }
        inceptionYear = "2025"
        description = "Easily fix KTS editor support for new IntelliJ versions"
    }
    signing {
        active = Active.ALWAYS
        mode = Signing.Mode.FILE
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                register("release-deploy") {
                    enabled = !isSnapshot
                    active = Active.RELEASE
                    url = "https://central.sonatype.com/api/v1/publisher"
                    applyMavenCentralRules = true
                    stagingRepository("build/release-deploy")
                }
            }
            nexus2 {
                register("snapshot-deploy") {
                    enabled = isSnapshot
                    active = Active.SNAPSHOT
                    snapshotUrl = "https://central.sonatype.com/repository/maven-snapshots/"
                    snapshotSupported = true
                    applyMavenCentralRules = true
                    stagingRepository("build/staging-deploy")
                }
            }
        }
    }
    release {
        github {
            enabled = false
        }
    }
}

tasks.withType<GenerateModuleMetadata>().configureEach {
    enabled = false
}
