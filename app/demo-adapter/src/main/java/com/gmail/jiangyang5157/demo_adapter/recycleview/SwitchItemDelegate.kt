package com.gmail.jiangyang5157.demo_adapter.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.recycleview.RecycleViewItemDelegate
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemSwitchBinding
import com.gmail.jiangyang5157.demo_adapter.item.SwitchItem

class SwitchItemDelegate :
    RecycleViewItemDelegate<SwitchItem, SwitchItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemSwitchBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: SwitchItem) {
        viewHolder.binding.tv.text = if (item.checked) {
            item.onText
        } else {
            item.offText
        }
        with(viewHolder.binding.sc) {
            setOnCheckedChangeListener(null)
            isChecked = item.checked
            setOnCheckedChangeListener { _, checked ->
                item.onCheckedChanged(item, viewHolder.adapterPosition, checked)
            }
        }
    }

    class ViewHolder(val binding: ItemSwitchBinding) : RecyclerView.ViewHolder(binding.root)
}
