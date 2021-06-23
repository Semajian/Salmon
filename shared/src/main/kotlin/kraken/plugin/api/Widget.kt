package kraken.plugin.api

class Widget {
    private var internal1: Long = 0

    external fun getChildren(): Array<Widget>?
    external fun getItem(): Item?
    external fun getTextBinary(): ByteArray?
    external fun getType(): Int

    fun getText(): String? {
        return String(getTextBinary() ?: return null, Charsets.US_ASCII)
    }
}