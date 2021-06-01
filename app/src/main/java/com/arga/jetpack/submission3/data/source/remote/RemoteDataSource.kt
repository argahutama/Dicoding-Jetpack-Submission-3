package com.arga.jetpack.submission3.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arga.jetpack.submission3.BuildConfig.API_KEY
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.data.source.remote.response.MovieResponse
import com.arga.jetpack.submission3.data.source.remote.response.TvShowResponse
import com.arga.jetpack.submission3.network.ApiClient
import com.arga.jetpack.submission3.util.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class RemoteDataSource {
    private val apiKey = API_KEY

    companion object {
        private var INSTANCE: RemoteDataSource? = null
        private val TAG = RemoteDataSource::class.java.toString()

        fun getInstance(): RemoteDataSource {
            if (INSTANCE == null)
                INSTANCE = RemoteDataSource()
            return INSTANCE!!
        }
    }

    fun getMovies(): LiveData<ApiResponse<List<MovieEntity>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MovieEntity>>>()
        val client = ApiClient.getApiService().getMovies(apiKey)

        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                resultMovies.value =
                    ApiResponse.success(response.body()?.results as List<MovieEntity>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d(TAG, "onFailure :${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultMovies
    }

    fun getMovieDetail(id: Int): LiveData<ApiResponse<MovieEntity>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<MovieEntity>>()
        val client = ApiClient.getApiService().getMovieDetails(id, apiKey)
        client.enqueue(object : Callback<MovieEntity> {
            override fun onResponse(call: Call<MovieEntity>, response: Response<MovieEntity>) {
                resultDetailMovie.value = ApiResponse.success(response.body() as MovieEntity)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieEntity>, t: Throwable) {
                Log.d(TAG, "onFailure :${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultDetailMovie
    }

    fun getTvShows(): LiveData<ApiResponse<List<TvShowEntity>>> {
        EspressoIdlingResource.increment()
        val resultTvShows = MutableLiveData<ApiResponse<List<TvShowEntity>>>()
        val client = ApiClient.getApiService().getTvShows(apiKey)
        client.enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                call: Call<TvShowResponse>,
                response: Response<TvShowResponse>
            ) {
                resultTvShows.value =
                    ApiResponse.success(response.body()?.results as List<TvShowEntity>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowResponse>, t: Throwable) {
                Log.d(TAG, "onFailure :${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultTvShows
    }

    fun getTvShowDetail(id: Int): LiveData<ApiResponse<TvShowEntity>> {
        EspressoIdlingResource.increment()
        val resultDetailTvShow = MutableLiveData<ApiResponse<TvShowEntity>>()
        val client = ApiClient.getApiService().getTvShowDetails(id, apiKey)
        client.enqueue(object : Callback<TvShowEntity> {
            override fun onResponse(call: Call<TvShowEntity>, response: Response<TvShowEntity>) {
                resultDetailTvShow.value = ApiResponse.success(response.body() as TvShowEntity)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvShowEntity>, t: Throwable) {
                Log.d(TAG, "onFailure :${t.message}")
                EspressoIdlingResource.decrement()
            }
        })

        return resultDetailTvShow
    }
}