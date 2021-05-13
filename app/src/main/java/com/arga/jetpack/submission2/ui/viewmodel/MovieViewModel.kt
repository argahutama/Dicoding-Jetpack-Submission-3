package com.arga.jetpack.submission2.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arga.jetpack.submission2.data.DataRepository
import com.arga.jetpack.submission2.data.source.local.entity.MovieEntity

class MovieViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val movie: LiveData<List<MovieEntity>> = dataRepository.getMovie()
    fun getMovieDetail(movieId: Int): LiveData<MovieEntity> = dataRepository.getMovieDetail(movieId)
}