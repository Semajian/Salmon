package kraken.plugin

import Plugin
import kraken.plugin.api.PluginContext

object Entry {
    private val plugin: PluginBase = Plugin()

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
}