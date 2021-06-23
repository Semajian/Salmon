package kraken.plugin.api

import shared.enums.Action

class Npc: Entity() {
    external fun getHealth(): Int
    external fun getId(): Int
    external fun getServerIndex(): Int

    fun interact(action: Action) {
       Actions.menu(action.id, getServerIndex(), 0, 0, 1)
    }
}