package kraken.plugin.api

class Player: Entity() {
    external fun getAnimationId(): Int
    external fun getServerIndex(): Int
    external fun getStatusBarFill(id: Int): Float
    external fun isAnimationPlaying(): Boolean
    external fun isMoving(): Boolean
}