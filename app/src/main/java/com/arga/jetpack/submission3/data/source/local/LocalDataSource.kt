package com.arga.jetpack.submission3.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.data.source.local.room.MyDao

open class LocalDataSource constructor(private val dao: MyDao) {

    fun getMovieDetail(movieId: Int): LiveData<MovieEntity> = dao.getMovieById(movieId)

    fun insertMovies(movies: List<MovieEntity>) {
        dao.insertMovies(movies)
    }

    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity> = dao.getFavoriteMovies()

    fun setFavoriteMovie(movie: MovieEntity, isFavorite: Boolean) {
        movie.isFavorite = isFavorite
        dao.updateMovie(movie)
    }

    fun getMovies(): DataSource.Factory<Int, MovieEntity> = dao.getMovies()


    fun getTvShows(): DataSource.Factory<Int, TvShowEntity> = dao.getTvShows()

    fun getTvShowDetail(tvShowId: Int): LiveData<TvShowEntity> = dao.getTvShowById(tvShowId)

    fun insertTvShows(tvShows: List<TvShowEntity>) {
        dao.insertTvShows(tvShows)
    }

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity> = dao.getFavoriteTvShows()

    fun setFavoriteTvShow(tvShow: TvShowEntity, isFavorite: Boolean) {
        tvShow.isFavorite = isFavorite
        dao.updateTvShow(tvShow)
    }

    fun updateMovie(movie: MovieEntity) = dao.updateMovie(movie)

    fun updateTvShow(tvShow: TvShowEntity) = dao.updateTvShow(tvShow)

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(catalogueDao: MyDao): LocalDataSource {
            if (INSTANCE == null) {
                INSTANCE =
                    LocalDataSource(catalogueDao)
            }
            return INSTANCE as LocalDataSource
        }
    }
}