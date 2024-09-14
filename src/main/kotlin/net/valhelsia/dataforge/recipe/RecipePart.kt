package net.valhelsia.dataforge.recipe

class RecipePart<T>(private val part: T) {
    fun get(): T {
        return this.part
    }

    companion object {
        fun <T> of(part: T): RecipePart<T> {
            return RecipePart(part)
        }
    }
}
