package com.gmail.jiangyang5157.transaction_presentation.ui.transaction

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.gmail.jiangyang5157.common.ext.toast
import com.gmail.jiangyang5157.kit.data.Resource
import com.gmail.jiangyang5157.kit.data.finance.Money
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import com.gmail.jiangyang5157.transaction_domain.entity.asString
import com.gmail.jiangyang5157.transaction_domain_kt.entity.REGEX_DATE_EEE_dd_MMM_yyyy_HH_mm_ss_zzz
import com.gmail.jiangyang5157.transaction_presentation.R
import com.gmail.jiangyang5157.transaction_presentation.vm.ReportViewModel
import com.gmail.jiangyang5157.transaction_presentation_base.ext.includedGst
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.title = "Transaction Details"
            it.subtitle = null
        }
        return inflater.inflate(R.layout.fragment_transaction, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: TransactionFragmentArgs by navArgs()
        setupTransaction(view, args.transactionId, Date(args.transactionImportedDate))
    }

    private fun setupTransaction(view: View, transactionId: String, transactionIdImportedDate: Date) {
        reportViewModel.getTransaction(transactionId, transactionIdImportedDate)
            .observe(
                viewLifecycleOwner,
                { resource ->
                    when (resource) {
                        is Resource.Completed -> {
                            Log.d("####", "getTransaction Resource.Completed data=${resource.data}")
                            setupTransaction(view, transactionId, resource.data)
                        }
                        is Resource.Loading -> {
                            Log.d("####", "getTransaction Resource.Loading pre-populate data=${resource.data}")
                            setupTransaction(view, transactionId, resource.data)
                        }
                        is Resource.Failed -> {
                            requireContext().toast(resource.error)
                        }
                    }
                }
            )
    }

    @SuppressLint("SetTextI18n")
    private fun setupTransaction(view: View, transactionId: String, entity: TransactionEntity?) {
        entity?.run {
            view.findViewById<TextView>(R.id.tv_id).text =
                "Transaction ID: $transactionId"
            view.findViewById<TextView>(R.id.tv_date).text =
                "Transaction Date: ${
                date.asString(
                    REGEX_DATE_EEE_dd_MMM_yyyy_HH_mm_ss_zzz
                )
                }"
            view.findViewById<TextView>(R.id.tv_description).text =
                "Transaction Description: $description"

            if (money.amount < Money(0).amount) {
                view.findViewById<TextView>(R.id.tv_money)?.apply {
                    text = "Debit: ${(-money).amount.toPlainString()}"
                    setTextColor(context.getColor(com.gmail.jiangyang5157.transaction_presentation_base.R.color.debitPrimary))
                }
                view.findViewById<TextView>(R.id.tv_gst).text =
                    "GST Included: ${(-money).includedGst().amount.toPlainString()}"
            } else {
                view.findViewById<TextView>(R.id.tv_money)?.apply {
                    text = "Credit: ${money.amount.toPlainString()}"
                    setTextColor(context.getColor(com.gmail.jiangyang5157.transaction_presentation_base.R.color.creditPrimary))
                }
                view.findViewById<TextView>(R.id.tv_gst).text = null
            }
        }
    }
}
