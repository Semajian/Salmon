package kraken.plugin.api

import shared.filters.SceneObjectFilter

object SceneObjects {
    @JvmStatic
    external fun closest(filter: Filter<SceneObject>): SceneObject?

    fun getClosest(id: Int): SceneObject? {
        return closest(SceneObjectFilter.byId(id))
    }
}