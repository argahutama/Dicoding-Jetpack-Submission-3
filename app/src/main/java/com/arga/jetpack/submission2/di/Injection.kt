package com.arga.jetpack.submission2.di

import com.arga.jetpack.submission2.data.DataRepository
import com.arga.jetpack.submission2.data.source.remote.RemoteRepository

object Injection {
    fun dataRepository(): DataRepository {
        val remoteRepository = RemoteRepository.getInstance()
        return DataRepository.getInstance(remoteRepository)!!
    }
}