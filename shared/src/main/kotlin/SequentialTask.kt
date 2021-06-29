package shared

abstract class SequentialTask(val interval: Int = 1) {
    var executed = false

    open fun completed(): Boolean { return true }
    open fun execute() {}
    open fun startOn(): Boolean { return true }
}