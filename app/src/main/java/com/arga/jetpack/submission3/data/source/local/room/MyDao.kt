package com.arga.jetpack.submission3.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity

@Dao
interface MyDao {
    @Query("SELECT * FROM movieentity WHERE id = :movieId")
    fun getMovieById(movieId: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movie: List<MovieEntity>): LongArray

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateMovie(movie: MovieEntity): Int

    @Query("SELECT * FROM movieentity where isfavorite = 1")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvshowentity WHERE id = :tvShowId")
    fun getTvShowById(tvShowId: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShow(tvShow: List<TvShowEntity>): LongArray

    @Update(onConflict = OnConflictStrategy.FAIL)
    fun updateTvShow(tvShow: TvShowEntity): Int

    @Query("SELECT * FROM tvshowentity where isfavorite = 1")
    fun getTvShows(): DataSource.Factory<Int, TvShowEntity>
}