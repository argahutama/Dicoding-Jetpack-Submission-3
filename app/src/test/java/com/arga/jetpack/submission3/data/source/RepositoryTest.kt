package com.arga.jetpack.submission3.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arga.jetpack.submission3.data.source.remote.RemoteRepository
import com.arga.jetpack.submission3.data.source.remote.interactor.GetMovieCallback
import com.arga.jetpack.submission3.data.source.remote.interactor.GetMovieDetailCallback
import com.arga.jetpack.submission3.data.source.remote.interactor.GetTvShowCallback
import com.arga.jetpack.submission3.data.source.remote.interactor.GetTvShowDetailCallback
import com.arga.jetpack.submission3.util.DummyData
import junit.framework.Assert.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class RepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteRepository::class.java)
    private val repository = FakeRepository(remote)

    private val movieResponses = DummyData.generateDummyMovies()
    private val movieId = movieResponses[0].id
    private val tvShowResponses = DummyData.generateDummyTvShows()
    private val tvShowId = tvShowResponses[0].id

    @Test
    fun getAllMovies() {
        doAnswer { invocation ->
            (invocation.arguments[0] as GetMovieCallback).onMovieListLoaded(movieResponses)
            null
        }.`when`(remote).getMovie(com.nhaarman.mockitokotlin2.any())
        val movieEntities = LiveDataTest.getValue(repository.getMovie())
        verify(remote).getMovie(com.nhaarman.mockitokotlin2.any())
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size, movieEntities.size)
    }

    @Test
    fun getAllTvShows() {
        doAnswer { invocation ->
            (invocation.arguments[0] as GetTvShowCallback).onTvShowListLoaded(tvShowResponses)
            null
        }.`when`(remote).getTvShow(com.nhaarman.mockitokotlin2.any())
        val tvShowEntities = LiveDataTest.getValue(repository.getTvShow())
        verify(remote).getTvShow(com.nhaarman.mockitokotlin2.any())
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.size, tvShowEntities.size)
    }

    @Test
    fun getMovieDetail() {
        doAnswer {
            val callback = it.arguments[1] as GetMovieDetailCallback
            callback.onMovieDetailLoaded(movieResponses[0])
            null
        }.`when`(remote).getMovieDetail(
            com.nhaarman.mockitokotlin2.any(),
            com.nhaarman.mockitokotlin2.any()
        )
        val result = movieId.let { repository.getMovieDetail(it) }.let { LiveDataTest.getValue(it) }
        verify(
            remote,
            times(1)
        ).getMovieDetail(
            com.nhaarman.mockitokotlin2.any(),
            com.nhaarman.mockitokotlin2.any()
        )
        assertEquals(movieResponses[0].id, result.id)
    }

    @Test
    fun getTvShowDetail() {
        doAnswer {
            val callback = it.arguments[1] as GetTvShowDetailCallback
            callback.onTvShowDetailLoaded(tvShowResponses[0])
            null
        }.`when`(remote).getTvShowDetail(
            com.nhaarman.mockitokotlin2.any(),
            com.nhaarman.mockitokotlin2.any()
        )
        val result = tvShowId.let { repository.getTvShowDetail(it) }.let { LiveDataTest.getValue(it) }
        verify(
            remote,
            times(1)
        ).getTvShowDetail(
            com.nhaarman.mockitokotlin2.any(),
            com.nhaarman.mockitokotlin2.any()
        )
        assertEquals(tvShowResponses[0].id, result.id)
    }
}