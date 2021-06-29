package shared.components

import kraken.plugin.api.Actions
import kraken.plugin.api.Widgets
import shared.enums.Action
import shared.enums.Location
import shared.enums.WidgetType
import shared.utilities.ComponentHash

object Lodestone {
    fun isWindowOpen(): Boolean {
        return Widgets.getGroupById(1092)?.getWidgets()?.get(51)?.getChildren()?.firstOrNull { widget -> widget.getType() == WidgetType.Text.id }?.getText() == "Lodestone Network"
    }

    fun openWindow() {
        Actions.menu(Action.Widget.id, 1, -1, ComponentHash.computeHash(1465, 18), 1)
    }

    fun teleportTo(location: Location) {
        Actions.menu(Action.Widget.id, 1, -1, ComponentHash.computeHash(1092, location.id), 1)
    }
}