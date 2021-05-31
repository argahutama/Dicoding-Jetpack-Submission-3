package com.arga.jetpack.submission3.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.vo.Resource

class MovieViewModel(private val dataRepository: DataRepository) : ViewModel() {
    val movies: LiveData<Resource<PagedList<MovieEntity>>> = dataRepository.getMovies()
    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> = dataRepository.getMovieDetail(movieId)
}