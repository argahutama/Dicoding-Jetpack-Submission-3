package com.arga.jetpack.submission3.data.source.remote.interactor

import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity

interface GetMovieDetailCallback {
    fun onMovieDetailLoaded(movieDetailResponse: MovieEntity)
}