package kraken.plugin.api

data class Area2di(val begin: Vector2i, val end: Vector2i) {
    constructor(x1: Int, y1: Int, x2: Int, y2: Int) : this(Vector2i(x1, y1), Vector2i(x2, y2))

    fun contains(vector: Vector2i): Boolean {
        return vector.x >= begin.x && vector.x <= end.x && vector.y >= begin.y && vector.y <= end.y
    }
}