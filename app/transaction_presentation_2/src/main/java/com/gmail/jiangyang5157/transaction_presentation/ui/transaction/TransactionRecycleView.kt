package com.gmail.jiangyang5157.transaction_presentation.ui.transaction

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.recycleview.MultiTypeRecycleViewAdapter
import com.gmail.jiangyang5157.transaction_presentation.ui.binding.DateItem
import com.gmail.jiangyang5157.transaction_presentation.ui.binding.DateItemViewBinder
import com.gmail.jiangyang5157.transaction_presentation.ui.binding.TransactionItem
import com.gmail.jiangyang5157.transaction_presentation.ui.binding.TransactionItemViewBinder

class TransactionRecycleView : RecyclerView {

    private val recycleViewAdapter = MultiTypeRecycleViewAdapter().apply {
        registerItemType(TransactionItem::class, TransactionItemViewBinder())
        registerItemType(DateItem::class, DateItemViewBinder())
    }

    constructor(context: Context) :
        super(context)

    constructor(context: Context, attrs: AttributeSet?) :
        super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    init {
        adapter = recycleViewAdapter
    }

    fun removeAllItems() {
        recycleViewAdapter.removeAllItems()
    }

    fun updateItems(items: List<Any>) {
        recycleViewAdapter.updateAllItems(items)
    }
}
