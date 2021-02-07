package com.arga.jetpack.submission2.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arga.jetpack.submission2.data.repository.local.entity.Item
import com.arga.jetpack.submission2.data.repository.local.entity.MovieDetail
import com.arga.jetpack.submission2.data.repository.remote.RemoteRepository
import com.arga.jetpack.submission2.data.repository.local.entity.TvShowDetail
import com.arga.jetpack.submission2.data.source.DataSource

class DataRepository(localRepository: LocalRepository, private val remoteRepository: RemoteRepository): DataSource {

    companion object{
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun getInstance(localRepository: LocalRepository, remoteRepository: RemoteRepository): DataRepository?{
            if (INSTANCE == null){
                synchronized(DataRepository::class.java){
                    if (INSTANCE == null)
                        INSTANCE = DataRepository(localRepository, remoteRepository)
                }
            }
            return INSTANCE
        }
    }

    override fun getMovie(): LiveData<List<Item>> {
        val movieLists = MutableLiveData<List<Item>>()
        remoteRepository.getMovie(object: RemoteRepository.GetMovieCallback{
            override fun onResponse(movieResponse: List<Item>) {
                movieLists.postValue(movieResponse)
            }

        })
        return movieLists
    }

    override fun getMovieDetail(movieId: String): LiveData<MovieDetail> {
        val movieDetail = MutableLiveData<MovieDetail>()
        remoteRepository.getMovieDetail(movieId, object: RemoteRepository.GetMovieDetailCallback{
            override fun onResponse(movieDetailResponse: MovieDetail) {
                movieDetail.postValue(movieDetailResponse)
            }

        })
        return movieDetail
    }

    override fun getTvShow(): LiveData<List<Item>> {
        val tvShowList = MutableLiveData<List<Item>>()
        remoteRepository.getTvShow(object: RemoteRepository.GetTvShowCallback{
            override fun onResponse(tvShowResponse: List<Item>) {
                tvShowList.postValue(tvShowResponse)
            }

        })
        return tvShowList
    }

    override fun getTvShowDetail(tvShowId: String): LiveData<TvShowDetail> {
        val tvShowDetail = MutableLiveData<TvShowDetail>()
        remoteRepository.getTvShowDetail(tvShowId, object: RemoteRepository.GetTvShowDetailCallback{
            override fun onResponse(tvShowDetailResponse: TvShowDetail) {
                tvShowDetail.postValue(tvShowDetailResponse)
            }

        })
        return tvShowDetail
    }
}