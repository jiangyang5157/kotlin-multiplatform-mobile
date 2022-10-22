package com.gmail.jiangyang5157.demo_adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.jiangyang5157.adapter.viewgroup.MultiTypeLinearLayoutManager
import com.gmail.jiangyang5157.adapter.viewgroup.MultiTypeViewGroupAdapter
import com.gmail.jiangyang5157.demo_adapter.databinding.ActivityScrollviewBinding
import com.gmail.jiangyang5157.demo_adapter.item.ChevronItem
import com.gmail.jiangyang5157.demo_adapter.item.ImageItem
import com.gmail.jiangyang5157.demo_adapter.item.SwitchItem
import com.gmail.jiangyang5157.demo_adapter.item.TextItem
import com.gmail.jiangyang5157.demo_adapter.item.TwoButtonsItem
import com.gmail.jiangyang5157.demo_adapter.item.VideoItem
import com.gmail.jiangyang5157.demo_adapter.viewgroup.ChevronItemDelegate
import com.gmail.jiangyang5157.demo_adapter.viewgroup.ImageItemDelegate
import com.gmail.jiangyang5157.demo_adapter.viewgroup.SwitchItemDelegate
import com.gmail.jiangyang5157.demo_adapter.viewgroup.TextItemDelegate
import com.gmail.jiangyang5157.demo_adapter.viewgroup.TwoButtonsDelegate
import com.gmail.jiangyang5157.demo_adapter.viewgroup.VideoItemDelegate
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScrollViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityScrollviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // config LinearLayout adapter for different item view types and delegate
        val adapter = MultiTypeViewGroupAdapter().apply {
            registerItemType(TextItem::class, TextItemDelegate())
            registerItemType(ChevronItem::class, ChevronItemDelegate())
            registerItemType(ImageItem::class, ImageItemDelegate())
            registerItemType(SwitchItem::class, SwitchItemDelegate())
            registerItemType(TwoButtonsItem::class, TwoButtonsDelegate())
            registerItemType(VideoItem::class, VideoItemDelegate())
        }
        adapter.attach(binding.svContent, MultiTypeLinearLayoutManager())

        // feed data
        adapter.addItem(ImageItem("ic_launcher Icon", R.mipmap.ic_launcher))

        adapter.addItem(VideoItem("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_2mb.mp4"))

        adapter.addItem(
            ChevronItem("Click Me!") { item, position ->
                Snackbar.make(
                    binding.root,
                    "${item::class.simpleName} at $position \nhashCode=${item.hashCode()}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        )

        adapter.addItem(
            SwitchItem(
                onText = "It's ON\n\nClick the Switch to turn OFF",
                offText = "It's OFF\n\nClick the Switch to turn On",
                checked = true
            ) { item, position, checked ->
                item.checked = checked
                adapter.updateItem(item, position)
            }
        )

        adapter.addItem(
            SwitchItem(
                onText = "It's ON\n\nClick the Switch to turn OFF",
                offText = "It's OFF\n\nClick the Switch to turn On",
                checked = false
            ) { item, position, checked ->
                item.checked = checked
                adapter.updateItem(item, position)
            }
        )

        adapter.addItem(
            TwoButtonsItem(
                leftText = "Insert into above",
                onLeftClicked = { position ->
                    val dateString =
                        SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date())

                    adapter.addItem(
                        ChevronItem("Add by  |  v  | at$dateString\nClick Me to remove this") { item, itemPosition ->
                            adapter.removeItem(itemPosition)
                            Snackbar.make(
                                binding.root,
                                "${item::class.simpleName} at $itemPosition\nhashCode=${item.hashCode()}",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        },
                        position
                    )
                },
                rightText = "Insert into below",
                onRightClicked = { position ->
                    val dateString =
                        SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Date())

                    adapter.addItems(
                        listOf(
                            ChevronItem("Add by  |  ^  | at $dateString\nClick Me to remove this and below") { item, itemPosition ->
                                adapter.removeItems(itemPosition, 2)
                                Snackbar.make(
                                    binding.root,
                                    "${item::class.simpleName} at $itemPosition\nhashCode=${item.hashCode()}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            },
                            ChevronItem("Add by  |  ^  | at $dateString\nClick Me to remove this and below") { item, itemPosition ->
                                adapter.removeItems(itemPosition, 2)
                                Snackbar.make(
                                    binding.root,
                                    "${item::class.simpleName} at $itemPosition\nhashCode=${item.hashCode()}",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        ),
                        position + 1
                    )
                }
            )
        )
    }
}