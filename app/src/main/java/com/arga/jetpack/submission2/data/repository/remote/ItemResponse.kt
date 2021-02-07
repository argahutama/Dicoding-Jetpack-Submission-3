package com.arga.jetpack.submission2.data.repository.remote

import com.google.gson.annotations.SerializedName

data class ItemResponse (
    @SerializedName("results")
    val results: List<Item>
)