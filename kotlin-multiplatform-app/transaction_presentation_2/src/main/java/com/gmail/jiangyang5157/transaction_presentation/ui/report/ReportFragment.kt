package com.gmail.jiangyang5157.transaction_presentation.ui.report

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.gmail.jiangyang5157.common.ext.toast
import com.gmail.jiangyang5157.kit.data.Resource
import com.gmail.jiangyang5157.transaction_domain.entity.DateStringConverter.Companion.REGEX_DATE_yyyy_MM_dd_HH_mm_ss
import com.gmail.jiangyang5157.transaction_domain.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain.entity.asString
import com.gmail.jiangyang5157.transaction_presentation.R
import com.gmail.jiangyang5157.transaction_presentation.ui.binding.DateItem
import com.gmail.jiangyang5157.transaction_presentation.ui.binding.TransactionItem
import com.gmail.jiangyang5157.transaction_presentation.ui.transaction.TransactionRecycleView
import com.gmail.jiangyang5157.transaction_presentation.vm.ReportViewModel
import com.gmail.jiangyang5157.transaction_presentation_base.ext.isSameDayAs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : Fragment() {

    private var rvTransaction: TransactionRecycleView? = null

    private val reportViewModel: ReportViewModel by viewModels()

    private var currentReport: ReportEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as? AppCompatActivity)?.supportActionBar?.let {
            it.title = "Report"
            it.subtitle = null
        }
        return inflater.inflate(R.layout.fragment_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTransaction = view.findViewById<TransactionRecycleView>(R.id.rv_transaction).apply {
            savedInstanceState?.getParcelable<Parcelable>(BUNDLE_KEY_RV_TRANSACTION_STATE)?.run {
                rvTransaction?.layoutManager?.onRestoreInstanceState(this)
            }
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        setupReport(rvTransaction)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_report, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                currentReport?.run {
                    reportViewModel.deleteReports(listOf(statement))
                }
                setupReport(rvTransaction)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        rvTransaction?.run {
            outState.putParcelable(
                BUNDLE_KEY_RV_TRANSACTION_STATE,
                layoutManager?.onSaveInstanceState()
            )
        }
    }

    private fun setupReport(transactionRecycleView: TransactionRecycleView?) {
        reportViewModel.getReports()
            .observe(
                viewLifecycleOwner
            ) { resource ->
                when (resource) {
                    is Resource.Completed -> {
                        Log.d(
                            "####",
                            "getReports Resource.Completed data.size=${resource.data?.size}"
                        )
                        setupReport(transactionRecycleView, resource.data)
                    }
                    is Resource.Loading -> {
                        Log.d(
                            "####",
                            "getReports Resource.Loading pre-populate data.size=${resource.data?.size}"
                        )
                        setupReport(transactionRecycleView, resource.data)
                    }
                    is Resource.Failed -> {
                        requireContext().toast(resource.error)
                    }
                }
            }
    }

    private fun setupReport(transactionRecycleView: TransactionRecycleView?, reports: List<ReportEntity>?) {
        reports?.sortedByDescending { it.statement.importedDate }?.run {
            currentReport = if (isEmpty()) {
                null
            } else {
                reportViewModel.deleteReports(dropLast(1).map { it.statement })
                last()
            }
            setupCurrentTransactions(transactionRecycleView)
        }
    }

    private fun setupCurrentTransactions(transactionRecycleView: TransactionRecycleView?) {
        currentReport?.run {
            (activity as? AppCompatActivity)?.supportActionBar?.let {
                it.title = "Report"
                it.subtitle = "Received at ${statement.importedDate.asString(REGEX_DATE_yyyy_MM_dd_HH_mm_ss)}"
            }
            val transactionItems = arrayListOf<Any>()
            var currentDateItem: DateItem? = null
            transactions.sortedByDescending { it.date }.forEach { entity ->
                if (currentDateItem == null || !entity.date.isSameDayAs(currentDateItem?.date)) {
                    currentDateItem = DateItem(entity.date).also {
                        // Add date row
                        transactionItems.add(it)
                    }
                }
                // Add transaction row
                transactionItems.add(TransactionItem(entity))
            }
            transactionRecycleView?.updateItems(transactionItems)
        } ?: run {
            (activity as? AppCompatActivity)?.supportActionBar?.let {
                it.title = "Report"
                it.subtitle = null
            }
            transactionRecycleView?.removeAllItems()
        }
    }

    companion object {
        private const val BUNDLE_KEY_RV_TRANSACTION_STATE = "BUNDLE_KEY_TRANSACTIONS_STATE"
    }
}
