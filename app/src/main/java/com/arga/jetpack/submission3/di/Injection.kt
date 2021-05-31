package com.arga.jetpack.submission3.di

import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.remote.RemoteRepository

object Injection {
    fun dataRepository(): DataRepository {
        val remoteRepository = RemoteRepository.getInstance()
        return DataRepository.getInstance(remoteRepository)!!
    }
}