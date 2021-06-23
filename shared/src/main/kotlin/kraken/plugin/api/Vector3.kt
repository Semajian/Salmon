package kraken.plugin.api

import kotlin.math.pow
import kotlin.math.sqrt

data class Vector3(val x: Float, val y: Float, val z: Float ) {
    fun distance(to: Vector3): Int {
        return sqrt((to.x - x).toDouble().pow(2) + (to.y - y).toDouble().pow(2) + (to.z - z).toDouble().pow(2)).toInt()
    }
}