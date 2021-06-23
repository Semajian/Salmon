package kraken.plugin.api

import shared.filters.WidgetItemFilter

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
        deposit(WidgetItemFilter.all(), 1)
    }
}