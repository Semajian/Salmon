package kraken.plugin.api

import shared.enums.Action

class SceneObject: Entity() {
    external fun getId(): Int
    external fun hidden(): Boolean

    fun interact(action: Action) {
        val position = getGlobalPosition()
        Actions.menu(action.id, getId(), position.x, position.y, 1)
    }

    fun interactAlternate(action: Action, alternateId: Int) {
        val position = getGlobalPosition()
        Actions.menu(action.id, alternateId, position.x, position.y, 1)
    }
}