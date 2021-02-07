package com.arga.jetpack.submission2.data.source.remote.interactor

import com.arga.jetpack.submission2.data.source.local.entity.MovieEntity

interface GetMovieCallback{
    fun onResponse(movieResponse: List<MovieEntity>)
}