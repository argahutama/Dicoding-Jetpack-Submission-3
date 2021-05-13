package com.arga.jetpack.submission2.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arga.jetpack.submission2.data.DataRepository
import com.arga.jetpack.submission2.data.DataSource
import com.arga.jetpack.submission2.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission2.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission2.data.source.remote.RemoteRepository
import com.arga.jetpack.submission2.data.source.remote.interactor.GetMovieCallback
import com.arga.jetpack.submission2.data.source.remote.interactor.GetMovieDetailCallback
import com.arga.jetpack.submission2.data.source.remote.interactor.GetTvShowCallback
import com.arga.jetpack.submission2.data.source.remote.interactor.GetTvShowDetailCallback

open class FakeRepository(private val dataRepository: DataRepository, private val remoteRepository: RemoteRepository):
    DataSource {
    override fun getMovie(): LiveData<List<MovieEntity>> {
        val movieList = MutableLiveData<List<MovieEntity>>()
        remoteRepository.getMovie(object : GetMovieCallback {
            override fun onMovieListLoaded(movieResponse: List<MovieEntity>) {
                movieList.postValue(movieResponse)
            }
        })
        return movieList
    }

    override fun getMovieDetail(movieId: Int): LiveData<MovieEntity> {
        val movieDetail = MutableLiveData<MovieEntity>()
        remoteRepository.getMovieDetail(movieId, object : GetMovieDetailCallback {
            override fun onMovieDetailLoaded(movieDetailResponse: MovieEntity) {
                movieDetail.postValue(movieDetailResponse)
            }
        })
        return movieDetail
    }

    override fun getTvShow(): LiveData<List<TvShowEntity>> {
        val tvShowList = MutableLiveData<List<TvShowEntity>>()
        remoteRepository.getTvShow(object: GetTvShowCallback {
            override fun onTvShowListLoaded(tvShowResponse: List<TvShowEntity>) {
                tvShowList.postValue(tvShowResponse)
            }
        })
        return  tvShowList
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<TvShowEntity> {
        val tvShowDetail = MutableLiveData<TvShowEntity>()
        remoteRepository.getTvShowDetail(tvShowId, object : GetTvShowDetailCallback {
            override fun onTvShowDetailLoaded(tvShowDetailResponse: TvShowEntity) {
                tvShowDetail.postValue(tvShowDetailResponse)
            }
        })
        return tvShowDetail
    }
}