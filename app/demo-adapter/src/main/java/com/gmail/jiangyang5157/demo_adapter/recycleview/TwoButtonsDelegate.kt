package com.gmail.jiangyang5157.demo_adapter.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.recycleview.RecycleViewItemDelegate
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemTwoButtonsBinding
import com.gmail.jiangyang5157.demo_adapter.item.TwoButtonsItem

class TwoButtonsDelegate :
    RecycleViewItemDelegate<TwoButtonsItem, TwoButtonsDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(
            ItemTwoButtonsBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: TwoButtonsItem) {
        with(viewHolder.binding.btnLeft) {
            text = item.leftText
            setOnClickListener {
                item.onLeftClicked(viewHolder.adapterPosition)
            }
        }
        with(viewHolder.binding.btnRight) {
            text = item.rightText
            setOnClickListener {
                item.onRightClicked(viewHolder.adapterPosition)
            }
        }
    }

    class ViewHolder(val binding: ItemTwoButtonsBinding) : RecyclerView.ViewHolder(binding.root)
}
