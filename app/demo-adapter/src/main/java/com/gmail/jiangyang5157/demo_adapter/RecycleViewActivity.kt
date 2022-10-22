package com.gmail.jiangyang5157.demo_adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.gmail.jiangyang5157.adapter.recycleview.ItemTouchHelperCallback
import com.gmail.jiangyang5157.adapter.recycleview.MultiTypeRecycleViewAdapter
import com.gmail.jiangyang5157.demo_adapter.databinding.ActivityRecycleviewBinding
import com.gmail.jiangyang5157.demo_adapter.item.*
import com.gmail.jiangyang5157.demo_adapter.recycleview.*
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class RecycleViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRecycleviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // config RecyclerView adapter for different item view types and delegate
        val adapter = MultiTypeRecycleViewAdapter().apply {
            registerItemType(TextItem::class, TextItemDelegate())
            registerItemType(ImageItem::class, ImageItemDelegate())
            registerItemType(ChevronItem::class, ChevronItemDelegate())
            registerItemType(SwitchItem::class, SwitchItemDelegate())
            registerItemType(TwoButtonsItem::class, TwoButtonsDelegate())
        }
        binding.rv.adapter = adapter

        // add drag-and-move and swiped-out handler
        ItemTouchHelper(
            ItemTouchHelperCallback(
                onItemMove = { fromPosition, toPosition ->
                    adapter.moveItem(fromPosition, toPosition)
                },
                onItemSwiped = { position ->
                    adapter.removeItem(position)
                }
            )
        ).attachToRecyclerView(binding.rv)

        // feed data
        adapter.addItem(TextItem("Long-press item to drag\nSwipe item to dismiss"))

        adapter.addItem(ImageItem("ic_launcher Icon", R.mipmap.ic_launcher))

        adapter.addItem(
            ChevronItem("Click Me!") { item, position ->
                Snackbar.make(
                    binding.root,
                    "${item::class.simpleName} at $position\nhashCode=${item.hashCode()}",
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
                        ChevronItem("Add by  |  v  | at $dateString\nClick Me to remove this") { item, itemPosition ->
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
