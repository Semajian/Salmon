package kraken.plugin.api

object GroundItems {
    @JvmStatic
    external fun closest(filter: Filter<GroundItem>): GroundItem?
}