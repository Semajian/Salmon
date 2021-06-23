package kraken.plugin.api

class GroundItem: Entity() {
    private var internal10: Int = 0

    external fun getAmount(): Int
    external fun getId(): Int
}