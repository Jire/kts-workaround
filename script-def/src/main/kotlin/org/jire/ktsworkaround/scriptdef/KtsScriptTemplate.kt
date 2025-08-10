package org.jire.ktsworkaround.scriptdef

import kotlin.script.experimental.annotations.KotlinScript

/**
 * @author Jire
 */
@KotlinScript(
    displayName = "KTS Script",
    fileExtension = "kts.kts",
    compilationConfiguration = KtsScriptConfiguration::class
)
abstract class KtsScriptTemplate
