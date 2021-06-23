enum class PluginTask {
    None,
    Fishing,
    Woodcutting;

    companion object {
        fun valueOf(value: Int): PluginTask {
            return values().firstOrNull { it.ordinal == value } ?: None
        }
    }
}