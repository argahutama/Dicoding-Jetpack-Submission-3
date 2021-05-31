package com.arga.jetpack.submission3.di

import android.content.Context
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.LocalDataSource
import com.arga.jetpack.submission3.data.source.local.room.MyDatabase
import com.arga.jetpack.submission3.data.source.remote.RemoteDataSource
import com.arga.jetpack.submission3.util.AppExecutor

object Injection {
    fun provideDataRepository(context: Context): DataRepository {
        val localRepository = LocalDataSource(
            MyDatabase.getInstance(context).dao()
        )
        val remoteRepository = RemoteDataSource.getInstance()
        val executors = AppExecutor()
        return remoteRepository.let { DataRepository.getInstance(localRepository, it, executors)!! }
    }
}