package kraken.plugin.api

import kotlin.math.pow
import kotlin.math.sqrt

data class Vector3i(val x: Int, val y: Int, val z: Int) {
    fun distance(to: Vector3i): Int {
        return sqrt((to.x - x).toDouble().pow(2) + (to.y - y).toDouble().pow(2) + (to.z - z).toDouble().pow(2)).toInt()
    }
}