# kts-workaround

_easily fix KTS editor support for new IntelliJ versions_

### First, add the lib to your project

```kotlin
dependencies {
    implementation("org.jire.ktsworkaround:script-def:1.1.0")
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
    private fun readResolve(): Any = ExampleCompilation
}
```

### And finally, in IntelliJ IDEA, go to

`File -> Invalidate Caches / Restart` and restart the IDE, then build your Gradle project. That's it!

### Go-to (CTRL clicking) Support

Please install my [KTS Support](https://plugins.jetbrains.com/plugin/28164-kts-support) plugin from the IntelliJ
Marketplace to get go-to (CTRL clicking) support for your KTS scripts.
