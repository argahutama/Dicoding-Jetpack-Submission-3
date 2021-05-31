package com.arga.jetpack.submission3.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.vo.Resource

class TvShowViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val tvShow: LiveData<Resource<PagedList<TvShowEntity>>> = dataRepository.getTvShows()
    fun getTvShowDetail(tvId: Int): LiveData<Resource<TvShowEntity>> =
        dataRepository.getTvShowDetail(tvId)
}