package com.gmail.jiangyang5157.transaction_data

import com.gmail.jiangyang5157.common.network.ApiResponseCallAdapterFactory
import com.gmail.jiangyang5157.transaction_data.local.ReportInMemory
import com.gmail.jiangyang5157.transaction_data.remote.ReportService
import com.gmail.jiangyang5157.transaction_data.repo.DefaultReportRepository
import com.gmail.jiangyang5157.transaction_domain.entity.ReportEntity
import com.gmail.jiangyang5157.transaction_domain.entity.StatementEntity
import com.gmail.jiangyang5157.transaction_domain.entity.TransactionEntity
import com.gmail.jiangyang5157.transaction_domain_kt.repository.IReportRepository
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalDataProvider {

    @Singleton
    @Provides
    fun reportInMemory(): ReportInMemory {
        return ReportInMemory()
    }
}

@InstallIn(SingletonComponent::class)
@Module
object RemoteDataProvider {

    @Singleton
    @Provides
    fun reportService(): ReportService {
        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .baseUrl("https://60df9ae9abbdd9001722d437.mockapi.io/")
            .addCallAdapterFactory(ApiResponseCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(ReportService::class.java)
    }
}

@InstallIn(ActivityRetainedComponent::class)
@Module
abstract class ReportRepositoryBinding {

    @ActivityRetainedScoped
    @Binds
    abstract fun reportRepository(arg: DefaultReportRepository): IReportRepository<ReportEntity, StatementEntity, TransactionEntity>
}
