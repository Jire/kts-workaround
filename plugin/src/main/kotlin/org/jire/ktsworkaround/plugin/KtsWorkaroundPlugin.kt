package org.jire.ktsworkaround.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * @author Jire
 */
class KtsWorkaroundPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.tasks.withType<KotlinCompile>().configureEach {
            @Suppress("MISSING_DEPENDENCY_SUPERCLASS_IN_TYPE_ARGUMENT")
            compilerOptions {
                freeCompilerArgs.addAll(ktsRequiredCompilerArgs)
            }
        }
    }

    companion object {
        @JvmStatic
        val ktsRequiredCompilerArgs: List<String> = listOf(
            "-Xuse-fir-lt=false",
            "-Xallow-any-scripts-in-source-roots",
        )
    }

}
