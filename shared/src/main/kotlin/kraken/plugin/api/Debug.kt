package kraken.plugin.api

object Debug {
    @JvmStatic
    external fun log(message: String)

    fun logException(exception: Exception) {
        log(exception.stackTraceToString())
    }
}