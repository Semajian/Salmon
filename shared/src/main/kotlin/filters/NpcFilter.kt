package shared.filters

import kraken.plugin.api.Filter
import kraken.plugin.api.Npc

object NpcFilter {
    fun byName(name: String): Filter<Npc> {
        return object: Filter<Npc> {
            override fun accept(t: Npc): Boolean {
                return t.getName().equals(name, true)
            }
        }
    }
}