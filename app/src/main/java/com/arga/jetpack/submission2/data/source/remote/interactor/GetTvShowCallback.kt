package com.arga.jetpack.submission2.data.source.remote.interactor

import com.arga.jetpack.submission2.data.source.local.entity.TvShowEntity

interface GetTvShowCallback{
    fun onResponse(tvShowResponse: List<TvShowEntity>)
}