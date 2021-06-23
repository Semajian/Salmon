package kraken.plugin.api

class PluginContext {
    private var internal1: Long = 0

    external fun setName(name: String)
    external fun setSdkVersion(version: Int)
}