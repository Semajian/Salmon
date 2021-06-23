package shared.extensions

import kraken.plugin.api.Vector2i
import kraken.plugin.api.Vector3i

object TypeCasts {
    fun Vector3i.toVector2i(): Vector2i {
        return Vector2i(x, y)
    }
}