package com.arga.jetpack.submission3.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.vo.Resource

class MovieViewModel(private val dataRepository: DataRepository) : ViewModel() {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = dataRepository.getMovies()
    private val selectedMovieId = MutableLiveData<Int>()

    fun setSelectedDetail(id: Int) {
        this.selectedMovieId.value = id
    }

    val selectedMovie: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(selectedMovieId) { id ->
            selectedMovieId.value?.let { dataRepository.getMovieDetail(id) }
        }

    fun setFavoriteMovie() {
        val movieResource = selectedMovie.value
        if (movieResource != null) {
            val movieEntity = movieResource.data
            if (movieEntity != null) {
                val newState = !movieEntity.isFavorite
                dataRepository.setFavoriteMovie(movieEntity, newState)
            }
        }
    }
}