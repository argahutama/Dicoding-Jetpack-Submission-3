package com.arga.jetpack.submission2.data.source.local.entity

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
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?
)