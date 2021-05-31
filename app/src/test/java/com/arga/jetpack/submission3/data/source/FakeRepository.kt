package com.arga.jetpack.submission3.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arga.jetpack.submission3.data.DataSource
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.data.source.remote.RemoteDataSource
import com.arga.jetpack.submission3.data.source.remote.interactor.GetMovieCallback
import com.arga.jetpack.submission3.data.source.remote.interactor.GetMovieDetailCallback
import com.arga.jetpack.submission3.data.source.remote.interactor.GetTvShowCallback
import com.arga.jetpack.submission3.data.source.remote.interactor.GetTvShowDetailCallback

open class FakeRepository(private val remoteDataSource: RemoteDataSource) :
    DataSource {
    override fun getMovies(): LiveData<List<MovieEntity>> {
        val movieList = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getMovies(object : GetMovieCallback {
            override fun onMovieListLoaded(movieResponse: List<MovieEntity>) {
                movieList.postValue(movieResponse)
            }
        })
        return movieList
    }

    override fun getMovieDetail(movieId: Int): LiveData<MovieEntity> {
        val movieDetail = MutableLiveData<MovieEntity>()
        remoteDataSource.getMovieDetail(movieId, object : GetMovieDetailCallback {
            override fun onMovieDetailLoaded(movieDetailResponse: MovieEntity) {
                movieDetail.postValue(movieDetailResponse)
            }
        })
        return movieDetail
    }

    override fun getTvShows(): LiveData<List<TvShowEntity>> {
        val tvShowList = MutableLiveData<List<TvShowEntity>>()
        remoteDataSource.getTvShows(object : GetTvShowCallback {
            override fun onTvShowListLoaded(tvShowResponse: List<TvShowEntity>) {
                tvShowList.postValue(tvShowResponse)
            }
        })
        return tvShowList
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<TvShowEntity> {
        val tvShowDetail = MutableLiveData<TvShowEntity>()
        remoteDataSource.getTvShowDetail(tvShowId, object : GetTvShowDetailCallback {
            override fun onTvShowDetailLoaded(tvShowDetailResponse: TvShowEntity) {
                tvShowDetail.postValue(tvShowDetailResponse)
            }
        })
        return tvShowDetail
    }
}