package kraken.plugin.api

import shared.Filters

object Bank {
    @JvmStatic
    external fun count(id: Int): Int
    @JvmStatic
    external fun deposit(filter: Filter<WidgetItem>, option: Int)
    @JvmStatic
    external fun first(filter: Filter<WidgetItem>): WidgetItem?
    @JvmStatic
    external fun getItems(): Array<WidgetItem>
    @JvmStatic
    external fun isOpen(): Boolean
    @JvmStatic
    external fun withdraw(filter: Filter<WidgetItem>, option: Int)

    fun contains(id: Int): Boolean {
        return count(id) > 0
    }

    fun containsAny(ids: Collection<Int>): Boolean {
        return getItems().any { item -> ids.contains(item.id) }
    }

    fun depositAll() {
        deposit(Filters.by { true }, 1)
    }

    fun depositAllExclude(exclude: Int) {
        deposit(Filters.by { item -> item.id != exclude }, 1)
    }

    fun depositAllExcludeMany(exclude: Collection<Int>) {
        deposit(Filters.by { item -> !exclude.contains(item.id) }, 1)
    }
}