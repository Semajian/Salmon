package kraken.plugin.api

import shared.enums.Action

object Actions {
    @JvmStatic
    external fun menu(id: Int, arg1: Int, arg2: Int, arg3: Int, arg4: Int)

    fun confirmItemUseDialogue() {
        menu(Action.Dialogue.id, 0, -1, 55509012, 1)
    }
}