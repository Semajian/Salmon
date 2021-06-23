package shared.filters

import kraken.plugin.api.Filter
import kraken.plugin.api.SceneObject

object SceneObjectFilter {
    fun byId(id: Int): Filter<SceneObject> {
        return object: Filter<SceneObject> {
            override fun accept(t: SceneObject): Boolean {
                return t.getId() == id
            }
        }
    }

    fun byName(name: String): Filter<SceneObject> {
        return object: Filter<SceneObject> {
            override fun accept(t: SceneObject): Boolean {
                return t.getName().equals(name, true)
            }
        }
    }
}