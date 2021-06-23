package kraken.plugin.api

import kotlin.math.pow
import kotlin.math.sqrt

data class Vector2i(val x: Int, val y: Int) {
    fun distance(to: Vector2i): Int {
        return sqrt((to.x - x).toDouble().pow(2) + (to.y - y).toDouble().pow(2)).toInt()
    }

    fun distance(to: Vector3i): Int {
        return sqrt((to.x - x).toDouble().pow(2) + (to.y - y).toDouble().pow(2)).toInt()
    }
}