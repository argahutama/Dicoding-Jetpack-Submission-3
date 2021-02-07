package com.arga.jetpack.submission2.network

import com.arga.jetpack.submission2.data.source.remote.response.ItemResponse
import com.arga.jetpack.submission2.data.source.local.entity.MovieDetail
import com.arga.jetpack.submission2.data.source.local.entity.TvShowDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getMovies(@Query("api_key") apiKey: String): Call<ItemResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int?,
                        @Query("api_key") apiKey: String?
    ) : Call<MovieDetail>

    @GET("tv/popular")
    fun getTvShows(@Query("api_key") apiKey: String?) : Call<ItemResponse>


    @GET("tv/{tv_id}")
    fun getTvShowDetails(@Path("tv_id") tvId: Int?,
                         @Query("api_key") apiKey: String?
    ) : Call<TvShowDetail>
}