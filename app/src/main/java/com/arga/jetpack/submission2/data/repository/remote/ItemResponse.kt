package com.arga.jetpack.submission2.data.repository.remote

import com.arga.jetpack.submission2.data.repository.local.entity.Item
import com.google.gson.annotations.SerializedName

data class ItemResponse (
    @SerializedName("results")
    val results: List<Item>
)