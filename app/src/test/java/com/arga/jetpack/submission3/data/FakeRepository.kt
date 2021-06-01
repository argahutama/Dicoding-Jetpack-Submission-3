package com.arga.jetpack.submission3.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.arga.jetpack.submission3.data.source.local.LocalDataSource
import com.arga.jetpack.submission3.data.source.local.entity.MovieEntity
import com.arga.jetpack.submission3.data.source.local.entity.TvShowEntity
import com.arga.jetpack.submission3.data.source.remote.ApiResponse
import com.arga.jetpack.submission3.data.source.remote.RemoteDataSource
import com.arga.jetpack.submission3.util.AppExecutor
import com.arga.jetpack.submission3.vo.Resource

class FakeRepository(
    private val remoteMovieDataSource: RemoteDataSource,
    private val localMovieDataSource: LocalDataSource,
    private val appThreadExecutors: AppExecutor
) : DataSource {

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieEntity>>(appThreadExecutors) {

            override fun loadDataFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localMovieDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> =
                remoteMovieDataSource.getMovies()

            public override fun saveCallResult(data: List<MovieEntity>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        response.id,
                        response.posterPath,
                        response.backdropPath,
                        response.title,
                        response.overview,
                        response.releasedDate,
                        response.voteAverage
                    )
                    movieList.add(movie)
                }

                localMovieDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowEntity>>(appThreadExecutors) {

            public override fun loadDataFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localMovieDataSource.getTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TvShowEntity>>> =
                remoteMovieDataSource.getTvShows()

            public override fun saveCallResult(data: List<TvShowEntity>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val tvShow = TvShowEntity(
                        response.id,
                        response.posterPath,
                        response.backdropPath,
                        response.name,
                        response.overview,
                        response.firstAirDate,
                        response.voteAverage
                    )
                    tvShowList.add(tvShow)
                }

                localMovieDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieEntity>(appThreadExecutors) {

            public override fun loadDataFromDB(): LiveData<MovieEntity> =
                localMovieDataSource.getMovieDetail(id)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<MovieEntity>> =
                remoteMovieDataSource.getMovieDetail(id)

            override fun saveCallResult(data: MovieEntity) {
                val movie = MovieEntity(
                    data.id,
                    data.posterPath,
                    data.backdropPath,
                    data.title,
                    data.overview,
                    data.releasedDate,
                    data.voteAverage
                )
                localMovieDataSource.updateMovie(movie)
            }

        }.asLiveData()
    }

    override fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, TvShowEntity>(appThreadExecutors) {

            public override fun loadDataFromDB(): LiveData<TvShowEntity> =
                localMovieDataSource.getTvShowDetail(id)

            override fun shouldFetch(data: TvShowEntity?): Boolean = data == null

            public override fun createCall(): LiveData<ApiResponse<TvShowEntity>> =
                remoteMovieDataSource.getTvShowDetail(id)

            override fun saveCallResult(data: TvShowEntity) {
                val tvShow = TvShowEntity(
                    data.id,
                    data.posterPath,
                    data.backdropPath,
                    data.name,
                    data.overview,
                    data.firstAirDate,
                    data.voteAverage
                )
                localMovieDataSource.updateTvShow(tvShow)
            }

        }.asLiveData()
    }

    override fun getFavoritesMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localMovieDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoritesTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localMovieDataSource.getFavoriteTvShows(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, isFavorite: Boolean) {
        appThreadExecutors.diskIO()
            .execute { localMovieDataSource.setFavoriteMovie(movie, isFavorite) }
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, isFavorite: Boolean) {
        appThreadExecutors.diskIO()
            .execute { localMovieDataSource.setFavoriteTvShow(tvShow, isFavorite) }
    }
}