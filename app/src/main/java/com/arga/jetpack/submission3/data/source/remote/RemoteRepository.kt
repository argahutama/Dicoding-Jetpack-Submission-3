package com.arga.jetpack.submission3.data.source.remote

import android.util.Log
import com.arga.jetpack.submission3.BuildConfig.API_KEY
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.data.source.remote.interactor.GetMovieCallback
import com.arga.jetpack.submission3.data.source.remote.interactor.GetMovieDetailCallback
import com.arga.jetpack.submission3.data.source.remote.interactor.GetTvShowCallback
import com.arga.jetpack.submission3.data.source.remote.interactor.GetTvShowDetailCallback
import com.arga.jetpack.submission3.data.source.remote.response.MovieResponse
import com.arga.jetpack.submission3.data.source.remote.response.TvShowResponse
import com.arga.jetpack.submission3.network.ApiClient
import com.arga.jetpack.submission3.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class RemoteRepository {
    private val apiKey = API_KEY
    private val apiClient = ApiClient

    companion object {
        private var INSTANCE: RemoteRepository? = null
        private val TAG = RemoteRepository::class.java.toString()

        fun getInstance(): RemoteRepository {
            if (INSTANCE == null)
                INSTANCE = RemoteRepository()
            return INSTANCE!!
        }
    }

    fun getMovie(getMovieCallback: GetMovieCallback) {
        EspressoIdlingResource.increment()
        apiClient.getApiService().getMovies(apiKey).enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                response.body()?.results?.let { getMovieCallback.onMovieListLoaded(it) }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })
    }

    fun getMovieDetail(movieId: Int, getMovieDetailCallback: GetMovieDetailCallback) {
        EspressoIdlingResource.increment()
        apiClient.getApiService().getMovieDetails(movieId, apiKey)
            .enqueue(object : Callback<MovieEntity> {
                override fun onResponse(call: Call<MovieEntity>, response: Response<MovieEntity>) {
                    getMovieDetailCallback.onMovieDetailLoaded(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }
            })
    }

    fun getTvShow(getTvShowCallback: GetTvShowCallback) {
        EspressoIdlingResource.increment()
        apiClient.getApiService().getTvShows(apiKey).enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                call: Call<TvShowResponse>,
                response: Response<TvShowResponse>
            ) {
                response.body()?.results?.let { getTvShowCallback.onTvShowListLoaded(it) }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.d(TAG, t.printStackTrace().toString())
            }
        })
    }

    fun getTvShowDetail(tvShowId: Int, getTvShowDetailCallback: GetTvShowDetailCallback) {
        EspressoIdlingResource.increment()
        apiClient.getApiService().getTvShowDetails(tvShowId, apiKey)
            .enqueue(object : Callback<TvShowEntity> {
                override fun onResponse(
                    call: Call<TvShowEntity>,
                    response: Response<TvShowEntity>
                ) {
                    getTvShowDetailCallback.onTvShowDetailLoaded(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<TvShowEntity>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }
            })
    }
}