package com.arga.jetpack.submission3.data.source.remote.response

import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    @SerializedName("results")
    val results: List<TvShowEntity>
)