package org.jire.ktsworkaround.scriptdef

import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.api.isStandalone
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

/**
 * @author Jire
 */
object KtsScriptConfiguration : ScriptCompilationConfiguration({
    isStandalone(false)
    ide {
        acceptedLocations(ScriptAcceptedLocation.Project)
    }
    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
        dependenciesFromClassContext(KtsScriptTemplate::class, wholeClasspath = true)
    }
}) {

    private fun readResolve(): Any = KtsScriptConfiguration

}
