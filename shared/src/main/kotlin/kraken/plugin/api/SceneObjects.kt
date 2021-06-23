package kraken.plugin.api

object SceneObjects {
    @JvmStatic
    external fun closest(filter: Filter<SceneObject>): SceneObject?
}