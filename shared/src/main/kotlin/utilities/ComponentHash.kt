package shared.utilities

object ComponentHash {
    fun computeHash(parentId: Int, childId: Int): Int {
        return parentId.shl(16).or(childId)
    }
}