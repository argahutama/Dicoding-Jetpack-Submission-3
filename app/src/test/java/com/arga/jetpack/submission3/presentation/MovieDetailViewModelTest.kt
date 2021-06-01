package com.arga.jetpack.submission3.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.presentation.viewmodel.MovieViewModel
import com.arga.jetpack.submission3.util.DummyData
import com.arga.jetpack.submission3.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieViewModel
    private val dummyMovie = DummyData.generateDummyMovies()[0]
    private val id = dummyMovie.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var detailObserver: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(dataRepository)
        viewModel.setSelectedDetail(id)
    }

    @Test
    fun testGetMovieEntity() {
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = Resource.success(dummyMovie)

        `when`(dataRepository.getMovieDetail(id)).thenReturn(movie)
        viewModel.selectedMovie.observeForever(detailObserver)
        verify(detailObserver).onChanged(movie.value)
    }

    @Test
    fun testSetFavoriteMovie() {
        val dummyTestMovie: Resource<MovieEntity> = Resource.success(dummyMovie)
        val movie: MutableLiveData<Resource<MovieEntity>> = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyTestMovie
        movie.value?.data?.isFavorite = false

        `when`(dataRepository.getMovieDetail(id)).thenReturn(movie)
        viewModel.selectedMovie.observeForever(detailObserver)

        dummyTestMovie.data?.let {
            doNothing().`when`(dataRepository).setFavoriteMovie(it, !it.isFavorite)
            viewModel.setFavoriteMovie()
            verify(dataRepository).setFavoriteMovie(it, !it.isFavorite)
        }
    }

    @Test
    fun testSetUnFavoriteMovie() {
        val dummyTestMovie: Resource<MovieEntity> = Resource.success(dummyMovie)
        val movie: MutableLiveData<Resource<MovieEntity>> = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyTestMovie
        movie.value?.data?.isFavorite = true

        `when`(dataRepository.getMovieDetail(id)).thenReturn(movie)
        viewModel.selectedMovie.observeForever(detailObserver)

        dummyTestMovie.data?.let {
            doNothing().`when`(dataRepository).setFavoriteMovie(it, !it.isFavorite)
            viewModel.setFavoriteMovie()
            verify(dataRepository).setFavoriteMovie(it, !it.isFavorite)
        }
    }
}