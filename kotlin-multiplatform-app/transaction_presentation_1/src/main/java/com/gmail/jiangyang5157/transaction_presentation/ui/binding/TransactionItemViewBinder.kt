package com.gmail.jiangyang5157.transaction_presentation.ui.binding

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.recycleview.RecycleViewItemDelegate
import com.gmail.jiangyang5157.kit.data.finance.Money
import com.gmail.jiangyang5157.transaction_presentation.R

class TransactionItemViewBinder :
    RecycleViewItemDelegate<TransactionItem, TransactionItemViewBinder.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMoney: TextView = itemView.findViewById(R.id.tv_money)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, item: TransactionItem) {
        viewHolder.itemView.setOnClickListener(item.onClickListener)
        viewHolder.tvDescription.text = item.transaction.description
        viewHolder.tvMoney.apply {
            if (item.transaction.money.amount < Money(0).amount) {
                text = "- $${(-item.transaction.money).amount.toPlainString()}"
                setTextColor(context.getColor(R.color.debitPrimary))
            } else {
                text = "+ $${item.transaction.money.amount.toPlainString()}"
                setTextColor(context.getColor(R.color.creditPrimary))
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false)
        )
    }
}
