package com.arga.jetpack.submission3.network

import com.arga.jetpack.submission3.data.source.remote.response.TvShowResponse
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.data.source.remote.response.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int?,
                        @Query("api_key") apiKey: String?
    ) : Call<MovieEntity>

    @GET("tv/popular")
    fun getTvShows(@Query("api_key") apiKey: String?) : Call<TvShowResponse>


    @GET("tv/{tv_id}")
    fun getTvShowDetails(@Path("tv_id") tvId: Int?,
                         @Query("api_key") apiKey: String?
    ) : Call<TvShowEntity>
}