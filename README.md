# kts-workaround

_easily fix KTS editor support for new IntelliJ versions_

### First, add the lib to your project

```kotlin
dependencies {
    implementation("org.jire.ktsworkaround:script-def:1.0.0")
}
```

### Then edit all your `ScriptCompilationConfiguration` bodys like this:

```kotlin
import org.jire.ktsworkaround.scriptdef.KtsScriptTemplate
import kotlin.script.experimental.api.ScriptAcceptedLocation
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.acceptedLocations
import kotlin.script.experimental.api.ide
import kotlin.script.experimental.api.isStandalone
import kotlin.script.experimental.jvm.dependenciesFromClassContext
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

object ExampleCompilation : ScriptCompilationConfiguration({
    isStandalone(false)
    ide {
        acceptedLocations(ScriptAcceptedLocation.Project)
    }
    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
        dependenciesFromClassContext(KtsScriptTemplate::class, wholeClasspath = true)
    }
}) {
    private fun readResolve(): Any = DefaultCompilation
}
```

### And finally, in IntelliJ IDEA, go to `File -> Invalidate Caches / Restart` and restart the IDE.
