package com.arga.jetpack.submission2.di

import android.content.Context
import com.arga.jetpack.submission2.data.repository.DataRepository
import com.arga.jetpack.submission2.data.repository.remote.RemoteRepository
import com.arga.jetpack.submission2.network.ApiClient

object Injection {
    fun dataRepository(context: Context?): DataRepository{
        val remoteRepository = RemoteRepository.getInstance(ApiClient)
        return DataRepository.getInstance(remoteRepository)!!
    }
}