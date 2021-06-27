package shared

import kraken.plugin.api.Actions
import kraken.plugin.api.Vector2i
import shared.enums.Action

class StaticEntity(private val id: Int, x: Int, y: Int) {
    private val coordinates = Vector2i(x, y)
    private var alternateId = id

    constructor(id: Int, alternateId: Int, x: Int, y: Int): this(id, x, y) {
        this.alternateId = alternateId
    }

    fun interact(action: Action) {
        Actions.menu(action.id, id, coordinates.x, coordinates.y, 1)
    }

    fun interactAlternate(action: Action) {
        Actions.menu(action.id, alternateId, coordinates.x, coordinates.y, 1)
    }
}