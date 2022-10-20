package com.gmail.jiangyang5157.demo_adapter.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.recycleview.RecycleViewItemDelegate
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemTextBinding
import com.gmail.jiangyang5157.demo_adapter.item.TextItem

class TextItemDelegate :
    RecycleViewItemDelegate<TextItem, TextItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemTextBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: TextItem) {
        viewHolder.binding.tv.text = item.title
    }

    class ViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root)
}
