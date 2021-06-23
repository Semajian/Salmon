package shared.filters

import kraken.plugin.api.Filter
import kraken.plugin.api.WidgetItem

object WidgetItemFilter {
    fun all(): Filter<WidgetItem> {
        return object: Filter<WidgetItem> {
            override fun accept(t: WidgetItem): Boolean {
                return true
            }
        }
    }

    fun byId(id: Int): Filter<WidgetItem> {
        return object: Filter<WidgetItem> {
            override fun accept(t: WidgetItem): Boolean {
                return t.id == id
            }
        }
    }
}