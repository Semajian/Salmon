package kraken.plugin.api

open class Entity {
    private var internal1: Long = 0
    private var internal2: Int = 0
    private var internal3: Long = 0

    external fun getGlobalPosition(): Vector3i
    external fun getScenePosition(): Vector3

    private external fun getNameBinary(): ByteArray?

    fun getName(): String {
        return String(getNameBinary() ?: return "Unknown", Charsets.US_ASCII)
    }
}