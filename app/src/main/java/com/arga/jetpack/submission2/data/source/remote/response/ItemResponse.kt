package com.arga.jetpack.submission2.data.source.remote.response

import com.arga.jetpack.submission2.data.source.local.entity.Item
import com.google.gson.annotations.SerializedName

data class ItemResponse (
    @SerializedName("results")
    val results: List<Item>
)