package utilities

object Time {
    fun formatTime(time: Long): String {
        val hours = (time / 3600000) % 24
        val minutes = (time / 60000) % 60
        val seconds = (time / 1000) % 60

        return "${hours}h ${minutes}m ${seconds}s"
    }

    fun perHour(time: Long, amount: Int): Int {
        return (amount.toDouble() * 3600000 / time.toDouble()).toInt()
    }
}