package com.gmail.jiangyang5157.transaction_presentation.ui.binding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.jiangyang5157.adapter.recycleview.RecycleViewItemDelegate
import com.gmail.jiangyang5157.transaction_domain.entity.asString
import com.gmail.jiangyang5157.transaction_domain_kt.entity.REGEX_DATE_EEE_dd_MMM_yyyy
import com.gmail.jiangyang5157.transaction_presentation.R

class DateItemViewBinder :
    RecycleViewItemDelegate<DateItem, DateItemViewBinder.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tv_date)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: DateItem) {
        viewHolder.tvDate.text = "${item.date.asString(REGEX_DATE_EEE_dd_MMM_yyyy)}"
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_date, parent, false))
    }
}