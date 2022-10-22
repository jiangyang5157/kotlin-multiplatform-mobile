package com.gmail.jiangyang5157.adapter.recycleview

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.ViewType
import com.gmail.jiangyang5157.adapter.ViewTypeRegistry
import java.util.*
import kotlin.reflect.KClass

open class MultiTypeRecycleViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val viewTypeRegistry = ViewTypeRegistry()
    private val items = mutableListOf<Any>()

    /**
     * The method should not be called after [RecyclerView.setAdapter], or you have to call the setAdapter again.
     */
    private fun <T> registerItemType(viewType: ViewType<T, RecycleViewItemDelegate<T, *>>) {
        viewTypeRegistry.register(viewType)
    }

    fun <T> registerItemType(clazz: Class<T>, delegate: RecycleViewItemDelegate<T, *>) {
        registerItemType(ViewType(clazz, delegate))
    }

    fun <T : Any> registerItemType(kClazz: KClass<T>, delegate: RecycleViewItemDelegate<T, *>) {
        registerItemType(kClazz.java, delegate)
    }

    /**
     * Use the index in [ViewTypeRegistry] as the item view type.
     */
    override fun getItemViewType(position: Int): Int {
        val index = viewTypeRegistry.indexOfFirst(items[position].javaClass)
        if (index != -1) {
            return index
        } else {
            throw IllegalStateException("Have you registered the ${items[position].javaClass.name} type and its delegate or binder?")
        }
    }

    override fun getItemId(position: Int): Long {
        return getItemDelegateByPosition(position).delegate.getItemId(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, itemViewType: Int): RecyclerView.ViewHolder {
        return getItemDelegateByItemViewType(itemViewType).delegate.onCreateViewHolder(
            parent.context,
            parent
        )
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        onBindViewHolder(viewHolder, position, emptyList())
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<Any>,
    ) {
        getItemDelegateByViewHolder(viewHolder).onBindViewHolder(
            viewHolder,
            items[position],
            payloads
        )
    }

    override fun onViewRecycled(viewHolder: RecyclerView.ViewHolder) {
        getItemDelegateByViewHolder(viewHolder).onViewRecycled(viewHolder)
    }

    override fun onViewAttachedToWindow(viewHolder: RecyclerView.ViewHolder) {
        getItemDelegateByViewHolder(viewHolder).onViewAttachedToWindow(viewHolder)
    }

    override fun onViewDetachedFromWindow(viewHolder: RecyclerView.ViewHolder) {
        getItemDelegateByViewHolder(viewHolder).onViewDetachedFromWindow(viewHolder)
    }

    override fun onFailedToRecycleView(viewHolder: RecyclerView.ViewHolder): Boolean {
        return getItemDelegateByViewHolder(viewHolder).onFailedToRecycleView(viewHolder)
    }

    private fun getItemDelegateByViewHolder(
        viewHolder: RecyclerView.ViewHolder,
    ): RecycleViewItemDelegate<Any, RecyclerView.ViewHolder> {
        @Suppress("UNCHECKED_CAST")
        return getItemDelegateByItemViewType(viewHolder.itemViewType).delegate
                as RecycleViewItemDelegate<Any, RecyclerView.ViewHolder>
    }

    private fun getItemDelegateByItemViewType(itemViewType: Int): ViewType<Any, RecycleViewItemDelegate<Any, *>> {
        @Suppress("UNCHECKED_CAST")
        return viewTypeRegistry.findByIndex(itemViewType)
    }

    private fun getItemDelegateByPosition(position: Int): ViewType<Any, RecycleViewItemDelegate<Any, *>> {
        val itemViewType = getItemViewType(position)
        return getItemDelegateByItemViewType(itemViewType)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Any, position: Int = this.items.size) {
        this.items.add(position, item)
        notifyItemInserted(position)
    }

    fun addItems(items: List<Any>, position: Int = this.items.size): Boolean {
        return this.items.addAll(position, items).also {
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
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAllItems(predicate: (Any) -> Boolean) {
        if (this.items.removeAll { predicate(it) }) {
            notifyDataSetChanged()
        }
    }

    fun updateItem(item: Any, position: Int) {
        this.items[position] = item
        notifyItemChanged(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateAllItems(items: List<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = this.items.removeAt(fromPosition)
        this.items.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun swapItems(positionA: Int, positionB: Int) {
        Collections.swap(this.items, positionA, positionB)
        notifyDataSetChanged()
    }

    fun findItemAt(position: Int): Any? {
        return if (position < items.size) {
            items[position]
        } else {
            null
        }
    }

    fun indexOfFirstItem(predicate: (Any) -> Boolean): Int {
        return this.items.indexOfFirst(predicate)
    }
}
