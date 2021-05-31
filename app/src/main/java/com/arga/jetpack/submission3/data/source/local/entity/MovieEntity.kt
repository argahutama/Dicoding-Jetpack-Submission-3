package com.arga.jetpack.submission3.data.source.local.entity

import com.google.gson.annotations.SerializedName

data class MovieEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val releasedDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)