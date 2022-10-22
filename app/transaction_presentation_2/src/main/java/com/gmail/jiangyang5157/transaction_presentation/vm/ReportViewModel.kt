package com.gmail.jiangyang5157.transaction_presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.jiangyang5157.common.data.Resource
import com.gmail.jiangyang5157.transaction_domain.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import com.gmail.jiangyang5157.transaction_domain.repo.ReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repo: ReportRepository<ReportEntity, StatementEntity, TransactionEntity>
) : ViewModel() {

    fun getReports(): LiveData<Resource<List<ReportEntity>, String>> {
        return repo.getReports().asLiveData()
    }

    fun getTransaction(
        id: String,
        importedDate: Date
    ): LiveData<Resource<TransactionEntity, String>> {
        return repo.findTransactionBy(id, importedDate).asLiveData()
    }

    fun deleteReports(statements: List<StatementEntity>) {
        viewModelScope.launch {
            repo.deleteReports(statements)
        }
    }
}
