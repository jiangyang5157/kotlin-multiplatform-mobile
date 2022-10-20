package com.gmail.jiangyang5157.demo_adapter.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.recycleview.RecycleViewItemDelegate
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemImageBinding
import com.gmail.jiangyang5157.demo_adapter.item.ImageItem

class ImageItemDelegate :
    RecycleViewItemDelegate<ImageItem, ImageItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemImageBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: ImageItem) {
        viewHolder.binding.tv.text = item.title
        viewHolder.binding.iv.setImageResource(item.imageResId)
    }

    class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)
}
