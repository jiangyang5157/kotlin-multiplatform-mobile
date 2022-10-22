package com.gmail.jiangyang5157.transaction_presentation

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ReportRepositoryBinding {

    @Binds
    abstract fun transactionLauncher(arg: DefaultTransactionLauncher): TransactionLauncher
}
