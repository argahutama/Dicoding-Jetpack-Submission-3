package com.arga.jetpack.submission2.data

import androidx.lifecycle.LiveData
import com.arga.jetpack.submission2.data.source.local.entity.Item
import com.arga.jetpack.submission2.data.source.local.entity.MovieDetail
import com.arga.jetpack.submission2.data.source.local.entity.TvShowDetail

interface DataSource {
    fun getMovie(): LiveData<List<Item>>
    fun getMovieDetail(movieId: String): LiveData<MovieDetail>
    fun getTvShow(): LiveData<List<Item>>
    fun getTvShowDetail(tvShowId: String): LiveData<TvShowDetail>
}