package kraken.plugin

import kraken.plugin.api.ConVar
import kraken.plugin.api.Debug
import kraken.plugin.api.PluginContext
import shared.GameTick
import kotlin.math.roundToInt
import kotlin.random.Random

abstract class PluginBase(private val name: String) {
    private val startTime: Long by lazy { System.currentTimeMillis() }

    fun getRuntime(): Long {
        return System.currentTimeMillis() - startTime
    }

    fun onConVarChange(conVar: ConVar, old: Int, new: Int) {
        try {
            conVarChange(conVar, old, new)
        }

        catch(exception: Exception) {
            Debug.logException(exception)
        }
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

    fun onWidgetChange(id: Int, visible: Boolean) {
        try {
            widgetChange(id, visible)
        }

        catch(exception: Exception) {
            Debug.logException(exception)
        }
    }

    protected open fun conVarChange(conVar: ConVar, old: Int, new: Int) {}
    protected open fun loop() {}
    protected open fun paint() {}
    protected open fun paintOverlay() {}
    protected open fun widgetChange(id: Int, visible: Boolean) {}
}