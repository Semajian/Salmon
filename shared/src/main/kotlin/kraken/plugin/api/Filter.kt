package kraken.plugin.api

interface Filter<in T> {
    fun accept(t: T): Boolean
}