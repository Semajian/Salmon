package shared.task

abstract class SequentialTask(val interval: Int = 1) {
    var executed = false

    open fun condition(): Boolean { return true}
    open fun completed(): Boolean { return true }
    open fun execute() {}
    open fun onInterval() {}
}