package com.gmail.jiangyang5157.adapter.viewgroup

import android.content.Context
import android.view.View
import android.view.ViewGroup

/*
Example:

```
data class FooItem(
    val value: String
)

// With ViewBinding
class FooItemDelegate : ViewGroupItemDelegate<FooItem, FooItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemTextBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: FooItem) {
        viewHolder.binding.tv.text = item.title
    }

    class ViewHolder(val binding: ItemFooBinding) : ViewGroupItemViewHolder(binding.root)
}

// Without ViewBinding
class FooItemDelegate : ViewGroupItemDelegate<FooItem, FooItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_foo, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: FooItem) {
        viewHolder.tvValue.text = item.value
    }

    class ViewHolder(view : View) : ViewGroupItemViewHolder(view) {
        val tvValue: TextView = view.findViewById(R.id.tvValue)
    }
}
```
 */
abstract class ViewGroupItemDelegate<T, VH : ViewGroupItemViewHolder> {

    /**
     * Called when [ViewGroup] needs a new view of the given type to represent an item.
     */
    abstract fun onCreateViewHolder(context: Context, parent: ViewGroup): VH

    /**
     * Called to reflect the contents of the view
     */
    abstract fun onBindViewHolder(viewHolder: VH, item: T)
}

open class ViewGroupItemViewHolder(val view: View) {

    val position: Int
        get() = (view.parent as ViewGroup).indexOfChild(view)
}
