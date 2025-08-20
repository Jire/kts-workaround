plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish")
    signing
}

group = "org.jire.ktsworkaround"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("gradle-plugin"))
}

kotlin {
    jvmToolchain(21)
}

java {
    withJavadocJar()
    withSourcesJar()
}

gradlePlugin {
    plugins {
        create("jire-kts-workaround") {
            id = "jire-kts-workaround"
            implementationClass = "org.jire.ktsworkaround.plugin.KtsWorkaroundPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "RspsCloud"
            url = uri("https://maven.rsps.cloud/")
            credentials(PasswordCredentials::class)
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
