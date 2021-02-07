package com.arga.jetpack.submission2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arga.jetpack.submission2.data.repository.DataRepository
import com.arga.jetpack.submission2.data.repository.remote.Item
import com.arga.jetpack.submission2.data.repository.remote.TvShowDetail

class TvShowViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val tvShow: LiveData<List<Item>> = dataRepository.getTvShow()
    fun getTvShowDetail(tvId: String): LiveData<TvShowDetail> = dataRepository.getTvShowDetail(tvId)
}