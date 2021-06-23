package kraken.plugin

import kraken.plugin.api.Debug
import kraken.plugin.api.PluginContext
import shared.GameTick
import shared.enums.PluginState

abstract class PluginBase(private val name: String) {
    var pluginState = PluginState.Idle

    private val startTime: Long by lazy { System.currentTimeMillis() }

    abstract fun loop()
    abstract fun paint()
    abstract fun paintOverlay()

    fun getRuntime(): Long {
        return System.currentTimeMillis() - startTime
    }

    fun onLoad(context: PluginContext): Boolean {
        try {
            context.setName(name)
        }

        catch(exception: Exception) {
            Debug.logException(exception)
            return false
        }

        return true
    }

    fun onLoop(): Int {
        try {
            loop()
        }

        catch(exception: Exception) {
            Debug.logException(exception)
        }

        return GameTick.milliseconds * 4
    }

    fun onPaint() {
        try {
            paint()
        }

        catch(exception: Exception) {
            Debug.logException(exception)
        }
    }

    fun onPaintOverlay() {
        try {
            paintOverlay()
        }

        catch(exception: Exception) {
            Debug.logException(exception)
        }
    }
}