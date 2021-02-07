package com.arga.jetpack.submission2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arga.jetpack.submission2.data.DataRepository
import com.arga.jetpack.submission2.data.source.local.entity.Item
import com.arga.jetpack.submission2.data.source.local.entity.MovieDetail

class MovieViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val movie: LiveData<List<Item>> = dataRepository.getMovie()
    fun getMovieDetail(movieId: String): LiveData<MovieDetail> = dataRepository.getMovieDetail(movieId)
}