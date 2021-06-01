package com.arga.jetpack.submission3.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.arga.jetpack.submission3.data.DataRepository
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.presentation.viewmodel.TvShowViewModel
import com.arga.jetpack.submission3.util.DummyData
import com.arga.jetpack.submission3.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowDetailViewModelTest {

    private lateinit var viewModel: TvShowViewModel
    private val dummyTvShow = DummyData.generateDummyTvShows()[0]
    private val id = dummyTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dataRepository: DataRepository

    @Mock
    private lateinit var detailObserver: Observer<Resource<TvShowEntity>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(dataRepository)
        viewModel.setSelectedDetail(id)
    }

    @Test
    fun testGetTvShowEntity() {
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = Resource.success(dummyTvShow)

        Mockito.`when`(dataRepository.getTvShowDetail(id)).thenReturn(tvShow)
        viewModel.selectedTvShow.observeForever(detailObserver)
        Mockito.verify(detailObserver).onChanged(tvShow.value)
    }

    @Test
    fun testSetFavoriteTvShow() {
        val dummyTestTvShow: Resource<TvShowEntity> = Resource.success(dummyTvShow)
        val tvshow: MutableLiveData<Resource<TvShowEntity>> =
            MutableLiveData<Resource<TvShowEntity>>()
        tvshow.value = dummyTestTvShow
        tvshow.value?.data?.isFavorite = false

        Mockito.`when`(dataRepository.getTvShowDetail(id)).thenReturn(tvshow)
        viewModel.selectedTvShow.observeForever(detailObserver)

        dummyTestTvShow.data?.let {
            Mockito.doNothing().`when`(dataRepository).setFavoriteTvShow(it, !it.isFavorite)
            viewModel.setFavoriteTvShow()
            Mockito.verify(dataRepository).setFavoriteTvShow(it, !it.isFavorite)
        }
    }

    @Test
    fun testUnFavoriteTvShow() {
        val dummyTestTvShow: Resource<TvShowEntity> = Resource.success(dummyTvShow)
        val tvshow: MutableLiveData<Resource<TvShowEntity>> =
            MutableLiveData<Resource<TvShowEntity>>()
        tvshow.value = dummyTestTvShow
        tvshow.value?.data?.isFavorite = true

        Mockito.`when`(dataRepository.getTvShowDetail(id)).thenReturn(tvshow)
        viewModel.selectedTvShow.observeForever(detailObserver)

        dummyTestTvShow.data?.let {
            Mockito.doNothing().`when`(dataRepository).setFavoriteTvShow(it, !it.isFavorite)
            viewModel.setFavoriteTvShow()
            Mockito.verify(dataRepository).setFavoriteTvShow(it, !it.isFavorite)
        }
    }

}