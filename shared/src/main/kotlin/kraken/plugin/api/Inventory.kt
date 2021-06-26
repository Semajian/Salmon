package kraken.plugin.api

object Inventory {
    @JvmStatic
    external fun count(id: Int): Int
    @JvmStatic
    external fun first(filter: Filter<WidgetItem>): WidgetItem?
    @JvmStatic
    external fun getItems(): Array<WidgetItem>

    fun contains(id: Int): Boolean {
        return count(id) > 0
    }

    fun containsAny(ids: Collection<Int>): Boolean {
        return getItems().any { item -> ids.contains(item.id) }
    }

    fun countMany(ids: Collection<Int>): Int {
        return getItems().filter { ids.contains(it.id) }.size
    }

    fun isEmpty(): Boolean {
        return getItems().isEmpty()
    }

    fun isFull(): Boolean {
        return getItems().size == 28
    }
}