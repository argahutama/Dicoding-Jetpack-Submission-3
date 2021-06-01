package com.arga.jetpack.submission3.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.vo.Resource

class TvShowViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val tvShows: LiveData<Resource<PagedList<TvShowEntity>>> = dataRepository.getTvShows()
    private val selectedTvShowId = MutableLiveData<Int>()

    fun setSelectedDetail(id: Int) {
        this.selectedTvShowId.value = id
    }

    val selectedTvShow: LiveData<Resource<TvShowEntity>> =
        Transformations.switchMap(selectedTvShowId) { id ->
            selectedTvShowId.value?.let { dataRepository.getTvShowDetail(id) }
        }

    fun setFavoriteTvShow() {
        val tvShowResource = selectedTvShow.value
        if (tvShowResource != null) {
            val tvShowEntity = tvShowResource.data
            if (tvShowEntity != null) {
                val newState = !tvShowEntity.isFavorite
                dataRepository.setFavoriteTvShow(tvShowEntity, newState)
            }
        }
    }
}