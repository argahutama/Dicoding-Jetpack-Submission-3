package com.arga.jetpack.submission2.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.arga.jetpack.submission2.data.DataRepository
import com.arga.jetpack.submission2.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission2.presentation.viewmodel.MovieViewModel
import com.arga.jetpack.submission2.util.DummyData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
class MovieViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: MovieViewModel? = null
    private var data = mock(DataRepository::class.java)

    @Before
    fun setUp(){
        viewModel = MovieViewModel(data)
    }

    @Test
    fun getMovieList(){
        val movie = MutableLiveData<List<MovieEntity>>()
        movie.value = DummyData.generateDummyMovies()
        `when`(data.getMovie()).thenReturn(movie)
        val observer = mock(Observer::class.java)
        viewModel?.movie?.observeForever(observer as Observer<List<MovieEntity>>)
        verify(data).getMovie()
    }

    @Test
    fun getMovieDetail(){
        val movie = MutableLiveData<MovieEntity>()
        movie.value = DummyData.generateDummyMovies()[0]
        `when`(data.getMovieDetail(movie.value!!.id)).thenReturn(movie)
        val observer = mock(Observer::class.java)
        viewModel?.getMovieDetail(movie.value!!.id)?.observeForever(observer as Observer<MovieEntity>)
        verify(data).getMovie()

        assertEquals(movie.value!!.id, viewModel?.getMovieDetail(movie.value!!.id)?.value?.id)
        assertEquals(movie.value!!.posterPath, viewModel?.getMovieDetail(movie.value!!.id)?.value?.posterPath)
        assertEquals(movie.value!!.backdropPath, viewModel?.getMovieDetail(movie.value!!.id)?.value?.backdropPath)
        assertEquals(movie.value!!.title, viewModel?.getMovieDetail(movie.value!!.id)?.value?.title)
        assertEquals(movie.value!!.overview, viewModel?.getMovieDetail(movie.value!!.id)?.value?.overview)
        assertEquals(movie.value!!.releasedDate, viewModel?.getMovieDetail(movie.value!!.id)?.value?.releasedDate)
        assertEquals(movie.value!!.voteAverage, viewModel?.getMovieDetail(movie.value!!.id)?.value?.voteAverage)
    }
}