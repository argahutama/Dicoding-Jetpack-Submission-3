package com.arga.jetpack.submission3.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.presentation.viewmodel.TvShowViewModel
import com.arga.jetpack.submission3.util.DummyData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@Suppress("UNCHECKED_CAST")
class TvShowViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var viewModel: TvShowViewModel? = null
    private var data = mock(DataRepository::class.java)

    @Before
    fun setUp(){
        viewModel = TvShowViewModel(data)
    }

    @Test
    fun getTvShowList(){
        val tvShow = MutableLiveData<List<TvShowEntity>>()
        tvShow.value = DummyData.generateDummyTvShows()
        `when`(data.getTvShow()).thenReturn(tvShow)
        val observer = mock(Observer::class.java)
        viewModel?.tvShow?.observeForever(observer as Observer<List<TvShowEntity>>)
        verify(data).getTvShow()
    }

    @Test
    fun getTvShowDetail(){
        val tvShow = MutableLiveData<TvShowEntity>()
        tvShow.value = DummyData.generateDummyTvShows()[0]
        `when`(data.getTvShowDetail(tvShow.value!!.id)).thenReturn(tvShow)
        val observer = mock(Observer::class.java)
        viewModel?.getTvShowDetail(tvShow.value!!.id)?.observeForever(observer as Observer<TvShowEntity>)
        verify(data).getTvShow()

        assertEquals(tvShow.value!!.id, viewModel?.getTvShowDetail(tvShow.value!!.id)?.value?.id)
        assertEquals(tvShow.value!!.posterPath, viewModel?.getTvShowDetail(tvShow.value!!.id)?.value?.posterPath)
        assertEquals(tvShow.value!!.backdropPath, viewModel?.getTvShowDetail(tvShow.value!!.id)?.value?.backdropPath)
        assertEquals(tvShow.value!!.name, viewModel?.getTvShowDetail(tvShow.value!!.id)?.value?.name)
        assertEquals(tvShow.value!!.overview, viewModel?.getTvShowDetail(tvShow.value!!.id)?.value?.overview)
        assertEquals(tvShow.value!!.firstAirDate, viewModel?.getTvShowDetail(tvShow.value!!.id)?.value?.firstAirDate)
        assertEquals(tvShow.value!!.voteAverage, viewModel?.getTvShowDetail(tvShow.value!!.id)?.value?.voteAverage)
    }
}
