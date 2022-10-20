package com.gmail.jiangyang5157.demo_adapter.viewgroup

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemDelegate
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemViewHolder
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemTextBinding
import com.gmail.jiangyang5157.demo_adapter.item.TextItem

class TextItemDelegate : ViewGroupItemDelegate<TextItem, TextItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemTextBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: TextItem) {
        viewHolder.binding.tv.text = item.title
    }

    class ViewHolder(val binding: ItemTextBinding) : ViewGroupItemViewHolder(binding.root)
}
