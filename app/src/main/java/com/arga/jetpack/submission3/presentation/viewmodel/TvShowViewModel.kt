package com.arga.jetpack.submission3.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity

class TvShowViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val tvShow: LiveData<List<TvShowEntity>> = dataRepository.getTvShow()
    fun getTvShowDetail(tvId: Int): LiveData<TvShowEntity> = dataRepository.getTvShowDetail(tvId)
}