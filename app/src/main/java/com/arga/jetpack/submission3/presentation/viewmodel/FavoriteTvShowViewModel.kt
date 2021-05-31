package com.arga.jetpack.submission3.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity

class FavoriteTvShowViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val favoriteTvShows: LiveData<PagedList<TvShowEntity>> = dataRepository.getFavoritesTvShows()

    fun setFavoriteMovie(tvShowEntity: TvShowEntity) {
        val isFavorite = !tvShowEntity.isFavorite
        dataRepository.setFavoriteTvShow(tvShowEntity, isFavorite)
    }

}