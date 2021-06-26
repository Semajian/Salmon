package shared

import kraken.plugin.api.Actions
import kraken.plugin.api.Vector2i
import shared.enums.Action

class StaticEntity(private val id: Int, x: Int, y: Int, private val alternateId: Int = id) {
    private val coordinates = Vector2i(x, y)

    fun interact(action: Action) {
        Actions.menu(action.id, id, coordinates.x, coordinates.y, 1)
    }

    fun interactAlternate(action: Action) {
        Actions.menu(action.id, alternateId, coordinates.x, coordinates.y, 1)
    }
}