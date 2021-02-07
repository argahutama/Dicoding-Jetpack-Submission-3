package com.arga.jetpack.submission2.data

import androidx.lifecycle.LiveData
import com.arga.jetpack.submission2.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission2.data.source.local.entity.TvShowEntity

interface DataSource {
    fun getMovie(): LiveData<List<MovieEntity>>
    fun getMovieDetail(movieId: Int): LiveData<MovieEntity>
    fun getTvShow(): LiveData<List<TvShowEntity>>
    fun getTvShowDetail(tvShowId: Int): LiveData<TvShowEntity>
}