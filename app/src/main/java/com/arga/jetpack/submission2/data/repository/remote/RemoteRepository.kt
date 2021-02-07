package com.arga.jetpack.submission2.data.repository.remote

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.arga.jetpack.submission2.BuildConfig.API_KEY
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

        fun getInstance(apiClient: ApiClient):RemoteRepository{
            if (INSTANCE == null)
                INSTANCE = RemoteRepository(apiClient)
            return INSTANCE!!
        }
    }

    interface GetMovieCallback{
        fun onResponse(movieResponse: List<Item>)
    }

    interface GetMovieDetailCallback{
        fun onResponse(movieDetailResponse: MovieDetail)
    }

    interface GetTvShowCallback{
        fun onResponse(tvShowResponse: List<Item>)
    }

    interface GetTvShowDetailCallback{
        fun onResponse(tvShowDetailResponse: TvShowDetail)
    }

    fun getMovie(getMovieCallback: GetMovieCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({
            apiClient.getApiService().getMovies(apiKey).enqueue(object: Callback<ItemResponse> {
                override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

                override fun onResponse(
                        call: Call<ItemResponse>,
                        response: Response<ItemResponse>
                ) {
                    response.body()?.results?.let { getMovieCallback.onResponse(it) }
                    EspressoIdlingResource.decrement()
                }

            })
        }, TIME_IN_MILLIS)
    }

    fun getMovieDetail(movieId: String, getMovieDetailCallback: GetMovieDetailCallback){
        EspressoIdlingResource.increment()
        handler.postDelayed({
            apiClient.getApiService().getMovieDetails(movieId, apiKey).enqueue(object: Callback<MovieDetail> {
                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                    getMovieDetailCallback.onResponse(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

            })
        }, TIME_IN_MILLIS)
    }

    fun getTvShow(getTvShowCallback: GetTvShowCallback){
        EspressoIdlingResource.increment()
        handler.postDelayed({
            apiClient.getApiService().getTvShows(apiKey).enqueue(object: Callback<ItemResponse> {
                override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

                override fun onResponse(
                        call: Call<ItemResponse>,
                        response: Response<ItemResponse>
                ) {
                    response.body()?.results?.let { getTvShowCallback.onResponse(it) }
                    EspressoIdlingResource.decrement()
                }

            })
        }, TIME_IN_MILLIS)
    }

    fun getTvShowDetail(tvShowId: String, getTvShowDetailCallback: GetTvShowDetailCallback){
        EspressoIdlingResource.increment()
        handler.postDelayed({
            apiClient.getApiService().getTvShowDetails(tvShowId, apiKey).enqueue(object: Callback<TvShowDetail> {
                override fun onFailure(call: Call<TvShowDetail>, t: Throwable) {
                    Log.d(TAG, t.printStackTrace().toString())
                }

                override fun onResponse(
                        call: Call<TvShowDetail>,
                        response: Response<TvShowDetail>
                ) {
                    getTvShowDetailCallback.onResponse(response.body()!!)
                    EspressoIdlingResource.decrement()
                }

            })
        }, TIME_IN_MILLIS)
    }
}