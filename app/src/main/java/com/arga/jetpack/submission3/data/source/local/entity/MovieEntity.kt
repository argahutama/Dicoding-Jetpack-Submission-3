package com.arga.jetpack.submission3.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieEntity(
    @PrimaryKey
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
    val voteAverage: Double?,
    var isFavorite: Boolean = false
)