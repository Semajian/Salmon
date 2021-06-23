package kraken.plugin.api

class WidgetGroup {
    private var internal1: Long = 0

    external fun getId(): Int
    external fun getWidgets(): Array<Widget>?
}