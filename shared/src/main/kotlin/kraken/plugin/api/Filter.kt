package kraken.plugin.api

interface Filter<T> {
    fun accept(t: T): Boolean
}