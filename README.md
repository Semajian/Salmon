## Salmon

An API interface and collection of plugins for the Kraken client

---

### Plugins

- Deep Sea Fishing (jellyfish, sailfish, swarm)
- Menaphos (fishing, woodcutting)

---

### Caveats

- Plugins must bundle the Kotlin runtime as the Kraken client does not include this

---

### Getting started

The example below demonstrates the process of defining the entry point of a new plugin

```kotlin
package kraken.plugin

import Plugin
import kraken.plugin.api.ConVar
import kraken.plugin.api.PluginContext

object Entry {
    private val plugin: PluginBase = Plugin()

    @JvmStatic
    fun onConVarChanged(conVar: ConVar, old: Int, new: Int) {
        plugin.onConVarChange(conVar, old, new)
    }

    @JvmStatic
    fun onLoaded(context: PluginContext): Boolean {
        return plugin.onLoad(context)
    }

    @JvmStatic
    fun onLoop(): Int {
        return plugin.onLoop()
    }

    @JvmStatic
    fun onPaint() {
        plugin.onPaint()
    }

    @JvmStatic
    fun onPaintOverlay() {
        plugin.onPaintOverlay()
    }

    @JvmStatic
    fun onWidgetVisibilityChanged(id: Int, visible: Boolean) {
        plugin.onWidgetChange(id, visible)
    }
}
```
