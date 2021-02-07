package com.arga.jetpack.submission2.data.repository.local.entity

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("tagline")
    val tagLine: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val releasedDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("name")
    val name: String?
)