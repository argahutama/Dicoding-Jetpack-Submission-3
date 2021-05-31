package com.arga.jetpack.submission3.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val favoriteMovies: LiveData<PagedList<MovieEntity>> = dataRepository.getFavoritesMovies()

    fun setFavoriteMovie(movieEntity: MovieEntity) {
        val isFavorite = !movieEntity.isFavorite
        dataRepository.setFavoriteMovie(movieEntity, isFavorite)
    }
}