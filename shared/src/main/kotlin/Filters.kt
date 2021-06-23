package shared

import kraken.plugin.api.Filter
import kraken.plugin.api.Npc
import kraken.plugin.api.SceneObject
import kraken.plugin.api.WidgetItem

object Filters {
    fun byNpc(predicate: (Npc) -> Boolean): Filter<Npc> {
        return object: Filter<Npc> {
            override fun accept(t: Npc): Boolean {
                return predicate(t)
            }
        }
    }

    fun bySceneObject(predicate: (SceneObject) -> Boolean): Filter<SceneObject> {
        return object: Filter<SceneObject> {
            override fun accept(t: SceneObject): Boolean {
                return predicate(t)
            }
        }
    }

    fun byWidgetItem(predicate: (WidgetItem) -> Boolean): Filter<WidgetItem> {
        return object: Filter<WidgetItem> {
            override fun accept(t: WidgetItem): Boolean {
                return predicate(t)
            }
        }
    }
}