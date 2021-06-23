package kraken.plugin.api

import shared.filters.NpcFilter

object Npcs {
    @JvmStatic
    external fun closest(filter: Filter<Npc>): Npc?

    fun getClosest(name: String): Npc? {
        return closest(NpcFilter.byName(name))
    }
}