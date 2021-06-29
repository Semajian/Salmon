package kraken.plugin

import kraken.plugin.api.ConVar
import kraken.plugin.api.Debug
import kraken.plugin.api.PluginContext
import shared.GameTick
import shared.task.SequentialTaskQueue
import kotlin.math.roundToInt
import kotlin.random.Random

abstract class PluginBase(private val name: String) {
    protected var nextLoopDelay = (GameTick.milliseconds * 4 * Random.nextDouble(1.0, 2.0)).roundToInt()
    protected val sequentialTaskQueue = SequentialTaskQueue()

    private val startTime by lazy { System.currentTimeMillis() }

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
            if (sequentialTaskQueue.isEmpty()) {
                loop()
            }

            else {
                val nextTask = sequentialTaskQueue.peek()

                if (!nextTask.executed && nextTask.condition()) {
                    nextTask.execute()
                    nextTask.executed = true
                }

                else {
                    if (nextTask.completed()) sequentialTaskQueue.dequeue() else nextTask.onInterval()
                }

                return nextTask.interval
            }
        }

        catch(exception: Exception) {
            Debug.logException(exception)
        }

        val delay = nextLoopDelay
        nextLoopDelay = (GameTick.milliseconds * 4 * Random.nextDouble(1.0, 2.0)).roundToInt()

        return delay
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