package com.arga.jetpack.submission2.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arga.jetpack.submission2.data.DataRepository
import com.arga.jetpack.submission2.data.source.remote.RemoteRepository
import com.arga.jetpack.submission2.data.source.remote.interactor.GetMovieCallback
import com.arga.jetpack.submission2.data.source.remote.interactor.GetMovieDetailCallback
import com.arga.jetpack.submission2.data.source.remote.interactor.GetTvShowCallback
import com.arga.jetpack.submission2.data.source.remote.interactor.GetTvShowDetailCallback
import com.arga.jetpack.submission2.util.DummyData
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class RepositoryTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val localRepository = mock(DataRepository::class.java)
    private val remoteRepository = mock(RemoteRepository::class.java)
    private val dataRepositoryTest = FakeRepository(localRepository, remoteRepository)

    private val movieList = DummyData.generateDummyMovies()
    private val movieId = movieList[0].id
    private val movieDetail = DummyData.generateDummyMovies()[0]
    private val tvShowList = DummyData.generateDummyTvShows()
    private val tvShowId = tvShowList[0].id
    private val tvShowDetail = DummyData.generateDummyTvShows()[0]

    private fun <T> anyOfT(type: Class<T>): T = any(type)

    private fun <T> eqOfT(obj: T): T = eq(obj)

    @Test
    fun getMovie() {
        doAnswer {
            val callback = it.arguments[0] as GetMovieCallback
            callback.onMovieListLoaded(movieList)
            null
        }.`when`(remoteRepository).getMovie(anyOfT(GetMovieCallback::class.java))

        val result = LiveDataTest.getValue(dataRepositoryTest.getMovie())
        verify(remoteRepository).getMovie(any(GetMovieCallback::class.java))
        assertEquals(movieList.size, result.size)
    }

    @Test
    fun getTvShow() {
        doAnswer {
            val callback = it.arguments[0] as GetTvShowCallback
            callback.onTvShowListLoaded(tvShowList)
            null
        }.`when`(remoteRepository).getTvShow(anyOfT(GetTvShowCallback::class.java))

        val result = LiveDataTest.getValue(dataRepositoryTest.getTvShow())
        verify(remoteRepository).getTvShow(any(GetTvShowCallback::class.java))
        assertEquals(tvShowList.size, result.size)
    }

    @Test
    fun getMovieDetail() {
        doAnswer {
            val callback = it.arguments[0] as GetMovieDetailCallback
            callback.onMovieDetailLoaded(movieDetail)
            null
        }.`when`(remoteRepository).getMovieDetail(
            eqOfT(movieId),
            anyOfT(GetMovieDetailCallback::class.java))

        val result = LiveDataTest.getValue(dataRepositoryTest.getMovieDetail(eqOfT(tvShowId)))
        verify(remoteRepository).getMovieDetail(eqOfT(tvShowId), anyOfT(GetMovieDetailCallback::class.java))
        assertEquals(movieId, result)
    }

    @Test
    fun getTvShowDetail() {
        doAnswer {
            val callback = it.arguments[0] as GetTvShowDetailCallback
            callback.onTvShowDetailLoaded(tvShowDetail)
            null
        }.`when`(remoteRepository).getTvShowDetail(eqOfT(tvShowId),
            anyOfT(GetTvShowDetailCallback::class.java))

        val result = LiveDataTest.getValue(dataRepositoryTest.getTvShowDetail(eqOfT(tvShowId)))
        verify(remoteRepository).getTvShowDetail(eqOfT(tvShowId), anyOfT(GetTvShowDetailCallback::class.java))
        assertEquals(tvShowId, result)
    }
}