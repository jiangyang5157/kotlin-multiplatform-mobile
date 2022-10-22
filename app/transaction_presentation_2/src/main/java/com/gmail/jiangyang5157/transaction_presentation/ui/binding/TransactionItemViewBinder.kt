package com.gmail.jiangyang5157.transaction_presentation.ui.binding

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.recycleview.RecycleViewItemDelegate
import com.gmail.jiangyang5157.common.data.finance.Money
import com.gmail.jiangyang5157.transaction_presentation.R
import com.gmail.jiangyang5157.transaction_presentation_base.ext.includedGst

class TransactionItemViewBinder :
    RecycleViewItemDelegate<TransactionItem, TransactionItemViewBinder.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMoney: TextView = itemView.findViewById(R.id.tv_money)
        val tvDescription: TextView = itemView.findViewById(R.id.tv_description)
        val tvGst: TextView = itemView.findViewById(R.id.tv_gst)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, item: TransactionItem) {
        viewHolder.tvDescription.text = item.transaction.description
        if (item.transaction.money.amount < Money(0).amount) {
            viewHolder.tvMoney.apply {
                text = "- $${(-item.transaction.money).amount.toPlainString()}"
                setTextColor(context.getColor(com.gmail.jiangyang5157.transaction_presentation_base.R.color.debitPrimary))
            }

            viewHolder.tvGst.apply {
                text =
                    "GST Included: ${(-item.transaction.money).includedGst().amount.toPlainString()}"
                visibility = View.VISIBLE
            }
        } else {
            viewHolder.tvMoney.apply {
                text = "+ $${item.transaction.money.amount.toPlainString()}"
                setTextColor(context.getColor(com.gmail.jiangyang5157.transaction_presentation_base.R.color.creditPrimary))
            }
            viewHolder.tvGst.apply {
                text = null
                visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_transaction, parent, false)
        )
    }
}
