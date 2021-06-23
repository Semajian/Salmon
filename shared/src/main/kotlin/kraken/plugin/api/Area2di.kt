package kraken.plugin.api

data class Area2di(val begin: Vector2i, val end: Vector2i) {
    fun contains(vector: Vector2i): Boolean {
        return vector.x >= begin.x && vector.x <= end.x && vector.y >= begin.y && vector.y <= end.y
    }
}