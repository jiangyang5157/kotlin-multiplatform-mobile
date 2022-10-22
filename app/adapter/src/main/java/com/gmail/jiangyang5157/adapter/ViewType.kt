package com.gmail.jiangyang5157.adapter

import android.util.Log

data class ViewType<T, D>(
    /** The class of the view data, including potential interaction callbacks */
    val clazz: Class<out T>,
    /** The delegate responsible for view creation and data binding */
    val delegate: D,
)

open class ViewTypeRegistry(
    initialCapacity: Int = 0,
    private val viewTypes: MutableList<ViewType<*, *>> = ArrayList(initialCapacity),
) {

    /**
     * If you have registered the class, it will override the original delegate.
     * The method is non-thread-safe so that you should not use it in concurrent operation.
     */
    fun <T, D> register(viewType: ViewType<T, D>) {
        val clazz = viewType.clazz
        if (unregister(clazz)) {
            Log.w(
                "ViewTypeRegistry",
                "The type ${clazz.simpleName} you originally registered is now overwritten."
            )
        }
        viewTypes.add(viewType)
    }

    /**
     * @return True if any element was removed from this collection.
     * @return False when no elements were removed and collection was not modified.
     */
    fun unregister(clazz: Class<*>): Boolean {
        return viewTypes.removeAll { it.clazz == clazz }
    }

    /**
     * If the subclass is already registered, the registered mapping is used.
     * If the subclass is not registered, then look for its parent class if is registered.
     * If the parent class is registered, the subclass is regarded as the parent class.
     *
     * @return The index of the first occurrence of the specified class;
     * @return -1 if does not contain the class.
     */
    fun indexOfFirst(clazz: Class<*>): Int {
        val index = viewTypes.indexOfFirst { it.clazz == clazz }
        if (index != -1) {
            return index
        }
        return viewTypes.indexOfFirst { it.clazz.isAssignableFrom(clazz) }
    }

    /**
     * @throws IndexOutOfBoundsException if the given index is out of range.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T, D> findByIndex(index: Int): ViewType<T, D> {
        return viewTypes[index] as ViewType<T, D>
    }
}
