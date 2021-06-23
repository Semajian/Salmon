package shared

import kraken.plugin.api.Actions
import kraken.plugin.api.Vector2i
import shared.enums.Action

class StaticEntity(private val coordinates: Vector2i, private val id: Int) {
    fun interact(action: Action) {
        Actions.menu(action.id, id, coordinates.x, coordinates.y, 1)
    }
}