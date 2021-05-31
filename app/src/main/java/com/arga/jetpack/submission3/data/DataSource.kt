package com.arga.jetpack.submission3.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.vo.Resource

interface DataSource {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>>
    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>>
    fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>>
    fun getFavoritesMovies(): LiveData<PagedList<MovieEntity>>
    fun getFavoritesTvShows(): LiveData<PagedList<TvShowEntity>>
    fun setFavoriteMovie(movie: MovieEntity, isFavorite: Boolean)
    fun setFavoriteTvShow(tvShow: TvShowEntity, isFavorite: Boolean)
}