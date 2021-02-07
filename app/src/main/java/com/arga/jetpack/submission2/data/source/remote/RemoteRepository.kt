package com.arga.jetpack.submission2.data.source.remote

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.arga.jetpack.submission2.BuildConfig.API_KEY
import com.arga.jetpack.submission2.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission2.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission2.data.source.remote.interactor.GetMovieCallback
import com.arga.jetpack.submission2.data.source.remote.interactor.GetMovieDetailCallback
import com.arga.jetpack.submission2.data.source.remote.interactor.GetTvShowCallback
import com.arga.jetpack.submission2.data.source.remote.interactor.GetTvShowDetailCallback
import com.arga.jetpack.submission2.data.source.remote.response.MovieResponse
import com.arga.jetpack.submission2.data.source.remote.response.TvShowResponse
import com.arga.jetpack.submission2.network.ApiClient
import com.arga.jetpack.submission2.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class RemoteRepository(apiClient: ApiClient) {
    private val apiKey = API_KEY
    private var handler = Handler(Looper.getMainLooper())
    private val apiClient = ApiClient

    companion object{
        private var INSTANCE: RemoteRepository? = null
        private val TAG = RemoteRepository::class.java.toString()
        private const val TIME_IN_MILLIS: Long = 1500

        fun getInstance(apiClient: ApiClient): RemoteRepository {
            if (INSTANCE == null)
                INSTANCE = RemoteRepository(apiClient)
            return INSTANCE!!
        }
    }

    fun getMovie(getMovieCallback: GetMovieCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({
            apiClient.getApiService().getMovies(apiKey).enqueue(object: Callback<MovieResponse> {
                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

                override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                    response.body()?.results?.let { getMovieCallback.onResponse(it) }
                    EspressoIdlingResource.decrement()
                }

            })
        }, TIME_IN_MILLIS)
    }

    fun getMovieDetail(movieId: Int, getMovieDetailCallback: GetMovieDetailCallback){
        EspressoIdlingResource.increment()
        handler.postDelayed({
            apiClient.getApiService().getMovieDetails(movieId, apiKey).enqueue(object: Callback<MovieEntity> {
                override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

                override fun onResponse(call: Call<MovieEntity>, response: Response<MovieEntity>) {
                    getMovieDetailCallback.onResponse(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

            })
        }, TIME_IN_MILLIS)
    }

    fun getTvShow(getTvShowCallback: GetTvShowCallback){
        EspressoIdlingResource.increment()
        handler.postDelayed({
            apiClient.getApiService().getTvShows(apiKey).enqueue(object: Callback<TvShowResponse> {
                override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

                override fun onResponse(call: Call<TvShowResponse>, response: Response<TvShowResponse>) {
                    response.body()?.results?.let { getTvShowCallback.onResponse(it) }
                    EspressoIdlingResource.decrement()
                }

            })
        }, TIME_IN_MILLIS)
    }

    fun getTvShowDetail(tvShowId: Int, getTvShowDetailCallback: GetTvShowDetailCallback){
        EspressoIdlingResource.increment()
        handler.postDelayed({
            apiClient.getApiService().getTvShowDetails(tvShowId, apiKey).enqueue(object: Callback<TvShowEntity> {
                override fun onFailure(call: Call<TvShowEntity>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

                override fun onResponse(call: Call<TvShowEntity>, response: Response<TvShowEntity>) {
                    getTvShowDetailCallback.onResponse(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

            })
        }, TIME_IN_MILLIS)
    }
}