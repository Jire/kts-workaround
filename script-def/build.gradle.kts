import org.jreleaser.model.Active

plugins {
    kotlin("jvm")
    `maven-publish`
    signing
    id("org.jreleaser")
}

group = "org.jire.ktsworkaround"
version = "1.0.0"

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
    signing {
        active = Active.ALWAYS
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                register("release-deploy") {
                    active = Active.RELEASE
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("target/staging-deploy")
                }
            }
            nexus2 {
                register("snapshot-deploy") {
                    active = Active.SNAPSHOT
                    snapshotUrl = "https://central.sonatype.com/repository/maven-snapshots/"
                    applyMavenCentralRules = true
                    snapshotSupported = true
                    closeRepository = true
                    releaseRepository = true
                    stagingRepository("target/staging-deploy")
                }
            }
        }
    }
    project {
        author("Jire")
        license.set("MIT")
        links {
            homepage = "https://github.com/Jire/ktsworkaround"
        }
        inceptionYear = "2025"
    }
    release {
        github {
            repoOwner = "Jire"
            overwrite = true
        }
    }
}
