package kraken.plugin.api

open class Entity {
    private var internal1: Long = 0
    private var internal2: Int = 0
    private var internal3: Long = 0

    external fun getGlobalPosition(): Vector3i
    external fun getNameBinary(): ByteArray?
    external fun getScenePosition(): Vector3

    fun getName(): String {
        return String(getNameBinary() ?: return "Unknown", Charsets.US_ASCII)
    }
}