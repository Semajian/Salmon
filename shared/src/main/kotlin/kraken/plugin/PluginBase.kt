package kraken.plugin

import kraken.plugin.api.Debug
import kraken.plugin.api.PluginContext
import shared.GameTick
import shared.enums.PluginState
import kotlin.math.roundToInt
import kotlin.random.Random

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



        return (GameTick.milliseconds * 4 * Random.nextDouble(1.0, 2.0)).roundToInt()
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