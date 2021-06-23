package kraken.plugin.api

object ImGui {
    @JvmStatic
    external fun beginChild(id: String): Boolean
    @JvmStatic
    external fun button(text: String): Boolean
    @JvmStatic
    external fun checkbox(text: String, state: Boolean): Boolean
    @JvmStatic
    external fun endChild()
    @JvmStatic
    external fun freeLine(from: Vector2i, to: Vector2i, color: Int)
    @JvmStatic
    external fun freePoly4(c1: Vector2i, c2: Vector2i, c3: Vector2i, c4: Vector2i, color: Int)
    @JvmStatic
    external fun freeText(text: String, position: Vector2i, color: Int)
    @JvmStatic
    external fun input(text: String, inputBuffer: ByteArray)
    @JvmStatic
    external fun intInput(text: String, value: Int): Int
    @JvmStatic
    external fun intSlider(text: String, value: Int, min: Int, max: Int): Int
    @JvmStatic
    external fun label(text: String)
}