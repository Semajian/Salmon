package kraken.plugin.api

import shared.enums.Action

class WidgetItem(id: Int, amount: Int, var slot: Int): Item(id, amount) {
    fun interact() {
        Actions.menu(Action.WidgetItem.id, 1, slot, 96534535, 1)
    }
}