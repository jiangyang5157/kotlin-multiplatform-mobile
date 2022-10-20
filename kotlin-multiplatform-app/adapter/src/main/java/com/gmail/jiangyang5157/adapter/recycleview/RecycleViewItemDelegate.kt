package com.gmail.jiangyang5157.adapter.recycleview

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/*
Example:

```
data class FooItem(
    val value: String
)

// With ViewBinding
class FooItemDelegate: RecycleViewItemDelegate<FooItem, FooItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemFooBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: FooItem) {
        viewHolder.binding.tvValue.text = item.value
    }

    class ViewHolder(val binding: ItemFooBinding) : RecyclerView.ViewHolder(binding.root)
}

// Without ViewBinding
class FooItemDelegate: RecycleViewItemDelegate<FooItem, FooItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_foo, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: FooItem) {
        viewHolder.tvValue.text = item.value
    }

    class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        val tvValue: TextView = view.findViewById(R.id.tvValue)
    }
}
```
 */
abstract class RecycleViewItemDelegate<T, VH : RecyclerView.ViewHolder> {

    /**
     * @see [RecyclerView.Adapter.onCreateViewHolder]
     */
    abstract fun onCreateViewHolder(context: Context, parent: ViewGroup): VH

    /**
     * @see [RecyclerView.Adapter.onBindViewHolder]
     */
    abstract fun onBindViewHolder(viewHolder: VH, item: T)

    /**
     * @see [RecyclerView.Adapter.onBindViewHolder]
     */
    open fun onBindViewHolder(viewHolder: VH, item: T, payloads: List<Any>) {
        onBindViewHolder(viewHolder, item)
    }

    /**
     * @see [RecyclerView.Adapter.getItemId]
     */
    open fun getItemId(item: T): Long = RecyclerView.NO_ID

    /**
     * @see [RecyclerView.Adapter.onFailedToRecycleView]
     */
    open fun onFailedToRecycleView(viewHolder: VH): Boolean {
        return false
    }

    /**
     * @see [RecyclerView.Adapter.onViewRecycled]
     */
    open fun onViewRecycled(viewHolder: VH) {}

    /**
     * @see [RecyclerView.Adapter.onViewAttachedToWindow]
     */
    open fun onViewAttachedToWindow(viewHolder: VH) {}

    /**
     * @see [RecyclerView.Adapter.onViewDetachedFromWindow]
     */
    open fun onViewDetachedFromWindow(viewHolder: VH) {}
}
