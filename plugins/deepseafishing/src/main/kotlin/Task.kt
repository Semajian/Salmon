enum class Task(val text: String) {
    None("None"),
    Jellyfish("Jellyfish"),
    Sailfish("Sailfish"),
    Swarm("Swarm");

    companion object {
        fun valueOf(value: Int): Task {
            return values().firstOrNull { it.ordinal == value } ?: None
        }
    }
}