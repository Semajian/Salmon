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

    fun depositAll() {
        deposit(Filters.byWidgetItem { true }, 1)
    }

    fun depositAllExclude(exclude: Int) {
        deposit(Filters.byWidgetItem { item -> item.id != exclude }, 1)
    }
}