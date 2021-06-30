package shared.components

import kraken.plugin.api.Actions
import shared.enums.Action
import shared.enums.Curse
import shared.enums.Prayer
import shared.utilities.ComponentHash

object Overhead {
    fun toggleCurse(curse: Curse) {
        Actions.menu(Action.Widget.id, 1, curse.id, ComponentHash.computeHash(1458, 39), 1)
    }

    fun togglePrayer(prayer: Prayer) {
        Actions.menu(Action.Widget.id, 1, prayer.id, ComponentHash.computeHash(1458, 39), 1)
    }
}