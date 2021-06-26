enum class Task(val text: String) {
    None("None"),
    Fishing("Fishing"),
    Woodcutting("Woodcutting");

    companion object {
        fun valueOf(value: Int): Task {
            return values().firstOrNull { it.ordinal == value } ?: None
        }
    }
}