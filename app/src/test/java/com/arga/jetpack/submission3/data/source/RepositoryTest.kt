package com.arga.jetpack.submission3.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.arga.jetpack.submission3.data.FakeRepository
import com.arga.jetpack.submission3.data.source.local.LocalDataSource
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.data.source.remote.RemoteDataSource
import com.arga.jetpack.submission3.util.AppExecutor
import com.arga.jetpack.submission3.util.AppExecutorTest
import com.arga.jetpack.submission3.util.DummyData
import com.arga.jetpack.submission3.data.LiveDataTestUtil
import com.arga.jetpack.submission3.vo.Resource
import com.arga.jetpack.submission3.util.UtilPagedList
import junit.framework.TestCase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
class RepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutor::class.java)
    private val testExecutors: AppExecutor =
        AppExecutor(AppExecutorTest(), AppExecutorTest(), AppExecutorTest())

    private val dataRepository = FakeRepository(remote, local, appExecutors)

    private val movieResponses = DummyData.generateDummyMovies()
    private val movieId = movieResponses[0].id

    private val tvShowResponses = DummyData.generateDummyTvShows()
    private val tvShowId = tvShowResponses[0].id

    @Test
    fun testGetAllMovies() {
        val dataMovieSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovies()).thenReturn(dataMovieSourceFactory)
        dataRepository.getMovies()

        val movieEntities =
            Resource.success(UtilPagedList.mockPagedList(DummyData.generateDummyMovies()))

        com.nhaarman.mockitokotlin2.verify(local).getMovies()
        TestCase.assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetAllTvShows() {
        val dataTvShowSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getTvShows()).thenReturn(dataTvShowSourceFactory)
        dataRepository.getTvShows()

        val tvShowEntities =
            Resource.success(UtilPagedList.mockPagedList(DummyData.generateDummyTvShows()))

        com.nhaarman.mockitokotlin2.verify(local).getTvShows()
        TestCase.assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun testGetDetailMovie() {
        val dummyDetailMovie = MutableLiveData<MovieEntity>()
        dummyDetailMovie.value = DummyData.generateDummyMovies()[0]
        `when`(local.getMovieDetail(movieId)).thenReturn(dummyDetailMovie)

        val resultMovie = LiveDataTestUtil.getValue(dataRepository.getMovieDetail(movieId))
        com.nhaarman.mockitokotlin2.verify(local).getMovieDetail(movieId)
        TestCase.assertNotNull(resultMovie.data)
        assertEquals(movieResponses[0].title, resultMovie.data?.title)
    }

    @Test
    fun testGetDetailTvShow() {
        val dummyDetailTvShow = MutableLiveData<TvShowEntity>()
        dummyDetailTvShow.value = DummyData.generateDummyTvShows()[0]
        `when`(local.getTvShowDetail(tvShowId)).thenReturn(dummyDetailTvShow)

        val resultTvShow = LiveDataTestUtil.getValue(dataRepository.getTvShowDetail(tvShowId))
        com.nhaarman.mockitokotlin2.verify(local).getTvShowDetail(tvShowId)
        TestCase.assertNotNull(resultTvShow.data)
        assertEquals(tvShowResponses[0].name, resultTvShow.data?.name)
    }

    @Test
    fun testGetFavoritesMovies() {
        val dataMovieSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataMovieSourceFactory)
        dataRepository.getFavoritesMovies()
        val movieEntities =
            Resource.success(UtilPagedList.mockPagedList(DummyData.generateDummyMovies()))

        com.nhaarman.mockitokotlin2.verify(local).getFavoriteMovies()
        TestCase.assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetFavoritesTvShows() {
        val dataTvShowSourceFactory =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavoriteTvShows()).thenReturn(dataTvShowSourceFactory)
        dataRepository.getFavoritesTvShows()
        val tvShowEntities =
            Resource.success(UtilPagedList.mockPagedList(DummyData.generateDummyTvShows()))

        com.nhaarman.mockitokotlin2.verify(local).getFavoriteTvShows()
        TestCase.assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun testSetFavoritesMovies() {
        val dataMovieDummy = DummyData.generateDummyMovies()[0].copy(isFavorite = false)
        val newFavState: Boolean = !dataMovieDummy.isFavorite

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteMovie(dataMovieDummy, newFavState)

        dataRepository.setFavoriteMovie(dataMovieDummy, newFavState)
        verify(local, times(1)).setFavoriteMovie(dataMovieDummy, newFavState)
    }

    @Test
    fun testSetUnFavoritesMovies() {
        val dataMovieDummy = DummyData.generateDummyMovies()[0].copy(isFavorite = true)
        val newFavState: Boolean = !dataMovieDummy.isFavorite

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteMovie(dataMovieDummy, newFavState)

        dataRepository.setFavoriteMovie(dataMovieDummy, newFavState)
        verify(local, times(1)).setFavoriteMovie(dataMovieDummy, newFavState)
    }

    @Test
    fun testSetFavoritesTvShows() {
        val dataTvShowDummy = DummyData.generateDummyTvShows()[0].copy(isFavorite = false)
        val newFavState: Boolean = !dataTvShowDummy.isFavorite

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteTvShow(dataTvShowDummy, newFavState)

        dataRepository.setFavoriteTvShow(dataTvShowDummy, newFavState)
        verify(local, times(1)).setFavoriteTvShow(dataTvShowDummy, newFavState)
    }

    @Test
    fun testSetUnFavoritesTvShows() {
        val dataTvShowDummy = DummyData.generateDummyTvShows()[0].copy(isFavorite = true)
        val newFavState: Boolean = !dataTvShowDummy.isFavorite

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteTvShow(dataTvShowDummy, newFavState)

        dataRepository.setFavoriteTvShow(dataTvShowDummy, newFavState)
        verify(local, times(1)).setFavoriteTvShow(dataTvShowDummy, newFavState)
    }
}