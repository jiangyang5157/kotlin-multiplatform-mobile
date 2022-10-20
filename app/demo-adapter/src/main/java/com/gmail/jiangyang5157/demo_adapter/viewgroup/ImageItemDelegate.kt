package com.gmail.jiangyang5157.demo_adapter.viewgroup

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemDelegate
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemViewHolder
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemImageBinding
import com.gmail.jiangyang5157.demo_adapter.item.ImageItem

class ImageItemDelegate : ViewGroupItemDelegate<ImageItem, ImageItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemImageBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: ImageItem) {
        viewHolder.binding.tv.text = item.title
        viewHolder.binding.iv.setImageResource(item.imageResId)
    }

    class ViewHolder(val binding: ItemImageBinding) : ViewGroupItemViewHolder(binding.root)
}
