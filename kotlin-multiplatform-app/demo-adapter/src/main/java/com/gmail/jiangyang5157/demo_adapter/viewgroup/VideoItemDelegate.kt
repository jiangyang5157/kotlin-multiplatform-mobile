package com.gmail.jiangyang5157.demo_adapter.viewgroup

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.MediaController
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemDelegate
import com.gmail.jiangyang5157.adapter.viewgroup.ViewGroupItemViewHolder
import com.gmail.jiangyang5157.demo_adapter.databinding.ItemVideoBinding
import com.gmail.jiangyang5157.demo_adapter.item.VideoItem

class VideoItemDelegate : ViewGroupItemDelegate<VideoItem, VideoItemDelegate.ViewHolder>() {

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: VideoItem) {
        viewHolder.binding.tv.text = item.url
        viewHolder.binding.vv.setVideoURI(Uri.parse(item.url))
        viewHolder.binding.vv.setOnPreparedListener {
            viewHolder.binding.vv.start()
            MediaController(viewHolder.binding.vv.context).also {
                it.setMediaPlayer(viewHolder.binding.vv)
                viewHolder.binding.vv.setMediaController(it)
            }
        }
    }

    class ViewHolder(val binding: ItemVideoBinding) : ViewGroupItemViewHolder(binding.root)
}
