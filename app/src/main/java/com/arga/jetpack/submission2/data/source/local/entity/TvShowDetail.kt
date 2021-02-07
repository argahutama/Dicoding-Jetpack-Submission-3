package com.arga.jetpack.submission2.data.source.local.entity

import com.google.gson.annotations.SerializedName

data class TvShowDetail (
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)