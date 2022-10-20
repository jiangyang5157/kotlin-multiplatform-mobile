package com.gmail.jiangyang5157.adapter.viewgroup

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import com.gmail.jiangyang5157.adapter.ViewType
import com.gmail.jiangyang5157.adapter.ViewTypeRegistry
import kotlin.reflect.KClass

/**
 * Client should not modify the children of [ViewGroup], after [attach] and before [detach].
 */
open class MultiTypeViewGroupAdapter {

    private val viewTypeRegistry = ViewTypeRegistry()

    /** Item and it's view holder */
    private val items = mutableListOf<Pair<Any, ViewGroupItemViewHolder?>>()

    private var viewGroup: ViewGroup? = null
    private var layoutManager: MultiTypeLayoutManager? = null
    private var isAttached: Boolean = false

    /**
     * The method should not be called after [attach], or you have to call the "attach" again.
     */
    private fun <T> registerItemType(viewType: ViewType<T, ViewGroupItemDelegate<T, *>>) {
        viewTypeRegistry.register(viewType)
    }

    fun <T> registerItemType(clazz: Class<T>, delegate: ViewGroupItemDelegate<T, *>) {
        registerItemType(ViewType(clazz, delegate))
    }

    fun <T : Any> registerItemType(kClazz: KClass<T>, delegate: ViewGroupItemDelegate<T, *>) {
        registerItemType(kClazz.java, delegate)
    }

    /**
     * Use the index in [ViewTypeRegistry] as the item view type.
     */
    fun getItemViewType(position: Int): Int {
        val index = viewTypeRegistry.indexOfFirst(items[position].first.javaClass)
        if (index != -1) {
            return index
        } else {
            throw IllegalStateException("Have you registered the ${items[position].first.javaClass.name} type and its delegate or binder?")
        }
    }

    private fun notifyDataSetChanged(itemTypeChanged: Boolean) {
        if (isAttached) {
            if (itemTypeChanged) {
                layoutManager!!.removeAllViews(viewGroup!!)
            }
            items.forEachIndexed { index, item ->
                val delegate = getItemDelegateByPosition(index).delegate
                if (itemTypeChanged) {
                    val viewHolder = delegate.onCreateViewHolder(viewGroup!!.context, viewGroup!!)
                    items[index] = Pair(item.first, viewHolder)
                    layoutManager!!.addView(viewGroup!!, items[index].second!!.view, index)
                }
                delegate.onBindViewHolder(items[index].second!!, items[index].first)
            }
        }
    }

    private fun notifyItemChanged(position: Int, itemTypeChanged: Boolean) {
        if (isAttached) {
            val delegate = getItemDelegateByPosition(position).delegate
            if (itemTypeChanged) {
                layoutManager!!.removeViewAt(viewGroup!!, position)
                val viewHolder = delegate.onCreateViewHolder(viewGroup!!.context, viewGroup!!)
                items[position] = Pair(items[position].first, viewHolder)
                layoutManager!!.addView(viewGroup!!, items[position].second!!.view, position)
            }
            delegate.onBindViewHolder(items[position].second!!, items[position].first)
        }
    }

    private fun notifyItemRangeRemoved(positionStart: Int, removedCount: Int) {
        if (isAttached) {
            val childCountBefore = viewGroup!!.childCount
            for (i in 0 until removedCount) {
                layoutManager!!.removeViewAt(viewGroup!!, positionStart)
            }
            if (positionStart + removedCount < childCountBefore) {
                moveChildrenIndex(
                    viewGroup!!,
                    layoutManager!!,
                    positionStart + removedCount,
                    childCountBefore - 1,
                    -removedCount
                )
            }
        }
    }

    private fun notifyItemRemoved(position: Int) {
        notifyItemRangeRemoved(position, 1)
    }

    private fun notifyItemRangeInserted(position: Int, size: Int) {
        if (isAttached) {
            val childCountBefore = viewGroup!!.childCount
            if (position < childCountBefore) {
                moveChildrenIndex(
                    viewGroup!!,
                    layoutManager!!,
                    childCountBefore - 1,
                    position,
                    size
                )
            }
            for (i in 0 until size) {
                val target = position + i
                val delegate = getItemDelegateByPosition(target).delegate
                val viewHolder = delegate.onCreateViewHolder(viewGroup!!.context, viewGroup!!)
                items[target] = Pair(items[target].first, viewHolder)
                layoutManager!!.addView(viewGroup!!, items[target].second!!.view, target)
                delegate.onBindViewHolder(items[target].second!!, items[target].first)
            }
        }
    }

    private fun notifyItemInserted(position: Int) {
        notifyItemRangeInserted(position, 1)
    }

    /**
     * Adjust children index after insert or remove any child view.
     */
    private fun moveChildrenIndex(
        parent: ViewGroup,
        layoutManager: MultiTypeLayoutManager,
        fromIndex: Int,
        toIndex: Int,
        step: Int,
    ) {
        when {
            step > 0 -> moveChildrenIndexForward(parent, layoutManager, fromIndex, toIndex, step)
            step < 0 -> moveChildrenIndexBackward(parent, layoutManager, fromIndex, toIndex, step)
            else -> return
        }
    }

    private fun moveChildrenIndexForward(
        parent: ViewGroup,
        layoutManager: MultiTypeLayoutManager,
        fromIndex: Int,
        toIndex: Int,
        step: Int,
    ) {
        if (step <= 0 || fromIndex < toIndex) {
            return
        }
        for (i in fromIndex..toIndex) {
            val cache = parent.getChildAt(i)
            layoutManager.removeViewAt(parent, i)
            val addTo = i + step
            val needed = addTo - parent.childCount
            val tmpViews = MutableList(if (needed > 0) needed else 0) { View(parent.context) }
            for (n in 0 until needed) {
                // Adding temporary views, avoid index out of bounds
                parent.addView(tmpViews[n])
            }
            val delegate = getItemDelegateByPosition(addTo).delegate
            layoutManager.addView(parent, cache, addTo)
            delegate.onBindViewHolder(items[addTo].second!!, items[addTo].first)
            // Removing temporary views if any
            tmpViews.forEach {
                (it.parent as? ViewGroup)?.removeView(it)
            }
        }
    }

    private fun moveChildrenIndexBackward(
        parent: ViewGroup,
        layoutManager: MultiTypeLayoutManager,
        fromIndex: Int,
        toIndex: Int,
        step: Int,
    ) {
        if (step >= 0 || fromIndex > toIndex) {
            return
        }
        for (i in fromIndex..toIndex) {
            val addTo = i + step
            val delegate = getItemDelegateByPosition(addTo).delegate
            delegate.onBindViewHolder(items[addTo].second!!, items[addTo].first)
        }
    }

    private fun getItemDelegateByItemViewType(itemViewType: Int): ViewType<Any, ViewGroupItemDelegate<Any, ViewGroupItemViewHolder>> {
        return viewTypeRegistry.findByIndex(itemViewType)
    }

    private fun getItemDelegateByPosition(position: Int): ViewType<Any, ViewGroupItemDelegate<Any, ViewGroupItemViewHolder>> {
        val itemViewType = getItemViewType(position)
        return getItemDelegateByItemViewType(itemViewType)
    }

    fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Any, position: Int = this.items.size) {
        this.items.add(position, Pair(item, null))
        notifyItemInserted(position)
    }

    fun addItems(items: List<Any>, position: Int = this.items.size): Boolean {
        return this.items.addAll(position, items.map { Pair(it, null) }).also {
            notifyItemRangeInserted(position, items.size)
        }
    }

    fun removeItem(position: Int): Any {
        return this.items.removeAt(position).also {
            notifyItemRemoved(position)
        }
    }

    fun removeItems(positionStart: Int, itemCount: Int) {
        if (positionStart < this.items.size) {
            val removedCount = if (positionStart + itemCount <= this.items.size) {
                itemCount
            } else {
                this.items.size - positionStart
            }
            for (i in 0 until removedCount) {
                this.items.removeAt(positionStart)
            }
            notifyItemRangeRemoved(positionStart, removedCount)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAllItems() {
        this.items.clear()
        notifyDataSetChanged(true)
    }

    fun updateItem(item: Any, position: Int, itemTypeChanged: Boolean = false) {
        val viewHolder = if (itemTypeChanged) null else items[position].second
        this.items[position] = Pair(item, viewHolder)
        notifyItemChanged(position, itemTypeChanged)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAllItems(items: List<Any>, itemTypeChanged: Boolean = false) {
        this.items.clear()
        this.items.addAll(
            items.mapIndexed { index, item ->
                val viewHolder = if (itemTypeChanged) null else this.items[index].second
                Pair(item, viewHolder)
            }
        )
        notifyDataSetChanged(itemTypeChanged)
    }

    /**
     * Attach the [ViewGroup] and [MultiTypeLayoutManager] to this adapter.
     * All existing child views will be replaced with the latest data here.
     */
    fun attach(viewGroup: ViewGroup, layoutManager: MultiTypeLayoutManager) {
        this.viewGroup = viewGroup
        this.layoutManager = layoutManager
        isAttached = true
        notifyDataSetChanged(true)
    }

    fun detach() {
        if (isAttached) {
            layoutManager!!.removeAllViews(viewGroup!!)
        }
        viewGroup = null
        layoutManager = null
        isAttached = false
    }
}
