package kraken.plugin.api

object Npcs {
    @JvmStatic
    external fun closest(filter: Filter<Npc>): Npc?
}