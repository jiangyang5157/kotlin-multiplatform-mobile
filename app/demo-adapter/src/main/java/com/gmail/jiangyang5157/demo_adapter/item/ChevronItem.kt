package com.gmail.jiangyang5157.demo_adapter.item

data class ChevronItem(
    val title: String,
    val onClicked: (item: ChevronItem, position: Int) -> Unit,
)
