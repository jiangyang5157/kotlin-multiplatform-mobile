package com.gmail.jiangyang5157.demo_adapter.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.recycleview.RecycleViewItemDelegate
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemChevronBinding
import com.gmail.jiangyang5157.demo_adapter.item.ChevronItem

class ChevronItemDelegate :
    RecycleViewItemDelegate<ChevronItem, ChevronItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemChevronBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: ChevronItem) {
        viewHolder.binding.tv.text = item.title
        viewHolder.binding.root.setOnClickListener {
            item.onClicked(item, viewHolder.adapterPosition)
        }
    }

    class ViewHolder(val binding: ItemChevronBinding) : RecyclerView.ViewHolder(binding.root)
}
