package com.gmail.jiangyang5157.demo_adapter.item

data class TwoButtonsItem(
    val leftText: String,
    val onLeftClicked: (position: Int) -> Unit,
    val rightText: String,
    val onRightClicked: (position: Int) -> Unit,
)
