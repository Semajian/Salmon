package shared

import kraken.plugin.api.Filter

object Filters {
    fun <T> by(predicate: (T) -> Boolean): Filter<T> {
        return object: Filter<T> {
            override fun accept(t: T): Boolean {
                return predicate(t)
            }
        }
    }
}