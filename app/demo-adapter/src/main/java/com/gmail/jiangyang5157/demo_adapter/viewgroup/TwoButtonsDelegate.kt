package com.gmail.jiangyang5157.demo_adapter.viewgroup

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemDelegate
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemViewHolder
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemTwoButtonsBinding
import com.gmail.jiangyang5157.demo_adapter.item.TwoButtonsItem

class TwoButtonsDelegate : ViewGroupItemDelegate<TwoButtonsItem, TwoButtonsDelegate.ViewHolder>() {

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
                item.onLeftClicked(viewHolder.position)
            }
        }
        with(viewHolder.binding.btnRight) {
            text = item.rightText
            setOnClickListener {
                item.onRightClicked(viewHolder.position)
            }
        }
    }

    class ViewHolder(val binding: ItemTwoButtonsBinding) : ViewGroupItemViewHolder(binding.root)
}
