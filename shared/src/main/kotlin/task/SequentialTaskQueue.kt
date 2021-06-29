package shared.task

class SequentialTaskQueue {
    private val queue = mutableListOf<SequentialTask>()

    fun dequeue(): SequentialTask {
        return queue.removeAt(0)
    }

    fun enqueue(task: SequentialTask) {
        queue.add(task)
    }

    fun enqueueFront(task: SequentialTask) {
        queue.add(0, task)
    }

    fun isEmpty(): Boolean {
        return queue.isEmpty()
    }

    fun peek(): SequentialTask {
        return queue[0]
    }
}