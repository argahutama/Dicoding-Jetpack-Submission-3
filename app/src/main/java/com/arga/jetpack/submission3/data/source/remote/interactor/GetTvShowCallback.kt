package com.arga.jetpack.submission3.data.source.remote.interactor

import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity

interface GetTvShowCallback {
    fun onTvShowListLoaded(tvShowResponse: List<TvShowEntity>)
}