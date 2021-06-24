package kraken.plugin.api

import shared.Filters
import shared.enums.Action
import shared.enums.Spirit

object Npcs {
    @JvmStatic
    external fun closest(filter: Filter<Npc>): Npc?

    fun tryFindAndInteractSpirit(spirit: Spirit): Boolean {
        closest(Filters.byNpc { npc -> npc.getId() == spirit.id })?.interact(Action.Npc1) ?: return false
        return true
    }
}