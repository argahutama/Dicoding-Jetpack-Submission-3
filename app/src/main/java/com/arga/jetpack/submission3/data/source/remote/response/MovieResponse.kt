package com.arga.jetpack.submission3.data.source.remote.response

import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results")
    val results: List<MovieEntity>
)