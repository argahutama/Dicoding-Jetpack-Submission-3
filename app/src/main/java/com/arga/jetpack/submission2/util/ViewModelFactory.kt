package com.arga.jetpack.submission2.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arga.jetpack.submission2.data.DataRepository
import com.arga.jetpack.submission2.di.Injection
import com.arga.jetpack.submission2.presentation.viewmodel.MovieViewModel
import com.arga.jetpack.submission2.presentation.viewmodel.TvShowViewModel

class ViewModelFactory(private val dataRepository: DataRepository): ViewModelProvider.NewInstanceFactory() {
    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.dataRepository())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> MovieViewModel(dataRepository) as T
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> TvShowViewModel(dataRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: " + modelClass.name)
        }
    }
}