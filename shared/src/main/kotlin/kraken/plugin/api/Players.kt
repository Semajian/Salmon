package kraken.plugin.api

object Players {
    @JvmStatic
    external fun closest(filter: Filter<Player>): Player?
    @JvmStatic
    external fun self(): Player?
}