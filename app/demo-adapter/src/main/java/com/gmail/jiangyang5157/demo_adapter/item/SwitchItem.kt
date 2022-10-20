package com.gmail.jiangyang5157.demo_adapter.item

data class SwitchItem(
    val onText: String,
    val offText: String,
    var checked: Boolean = false,
    val onCheckedChanged: (item: SwitchItem, position: Int, checked: Boolean) -> Unit
)
