package com.arga.jetpack.submission3.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.di.Injection
import com.arga.jetpack.submission3.presentation.viewmodel.FavoriteMovieViewModel
import com.arga.jetpack.submission3.presentation.viewmodel.FavoriteTvShowViewModel
import com.arga.jetpack.submission3.presentation.viewmodel.MovieViewModel
import com.arga.jetpack.submission3.presentation.viewmodel.TvShowViewModel

class ViewModelFactory(private val dataRepository: DataRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideDataRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) ->
                MovieViewModel(dataRepository) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) ->
                TvShowViewModel(dataRepository) as T
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) ->
                FavoriteMovieViewModel(dataRepository) as T
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) ->
                FavoriteTvShowViewModel(dataRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: " + modelClass.name)
        }
    }
}