package com.gmail.jiangyang5157.demo_adapter.viewgroup

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemDelegate
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemViewHolder
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemChevronBinding
import com.gmail.jiangyang5157.demo_adapter.item.ChevronItem

class ChevronItemDelegate : ViewGroupItemDelegate<ChevronItem, ChevronItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemChevronBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: ChevronItem) {
        viewHolder.binding.tv.text = item.title
        viewHolder.binding.root.setOnClickListener {
            item.onClicked(item, viewHolder.position)
        }
    }

    class ViewHolder(val binding: ItemChevronBinding) : ViewGroupItemViewHolder(binding.root)
}
