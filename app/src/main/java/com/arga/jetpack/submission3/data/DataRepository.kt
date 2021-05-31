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

class DataRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    val executor: AppExecutor
) : DataSource {

    companion object {
        @Volatile
        private var INSTANCE: DataRepository? = null

        fun getInstance(
            localDataSource: LocalDataSource,
            remoteDataSource: RemoteDataSource,
            executor: AppExecutor
        ): DataRepository? {
            if (INSTANCE == null) synchronized(DataRepository::class.java) {
                if (INSTANCE == null)
                    INSTANCE = DataRepository(localDataSource, remoteDataSource, executor)
            }

            return INSTANCE
        }
    }

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieEntity>>(executor) {

            override fun loadDataFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>): Boolean = false

            override fun createCall(): LiveData<ApiResponse<List<MovieEntity>>> =
                remoteDataSource.getMovies()

            override fun saveCallResult(data: List<MovieEntity>) {
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
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieEntity>(executor) {
            override fun loadDataFromDB(): LiveData<MovieEntity> =
                localDataSource.getMovieDetail(id)

            override fun shouldFetch(data: MovieEntity): Boolean = false

            override fun createCall(): LiveData<ApiResponse<MovieEntity>> =
                remoteDataSource.getMovieDetail(id)

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
                localDataSource.updateMovie(movie)
            }
        }.asLiveData()
    }

    override fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowEntity>, List<TvShowEntity>>(executor) {
            override fun loadDataFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>): Boolean = data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvShowEntity>>> =
                remoteDataSource.getTvShows()

            override fun saveCallResult(data: List<TvShowEntity>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val tvShow = TvShowEntity(
                        response.id,
                        response.posterPath,
                        response.backdropPath,
                        response.name,
                        response.firstAirDate,
                        response.overview,
                        response.voteAverage
                    )
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTvShows(tvShowList)
            }
        }.asLiveData()
    }


    override fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>> {
        return object :
            NetworkBoundResource<TvShowEntity, TvShowEntity>(executor) {
            override fun loadDataFromDB(): LiveData<TvShowEntity> =
                localDataSource.getTvShowDetail(id)

            override fun shouldFetch(data: TvShowEntity): Boolean = false

            override fun createCall(): LiveData<ApiResponse<TvShowEntity>> =
                remoteDataSource.getTvShowDetail(id)

            override fun saveCallResult(data: TvShowEntity) {
                val tvShow = TvShowEntity(
                    data.id,
                    data.posterPath,
                    data.backdropPath,
                    data.name,
                    data.firstAirDate,
                    data.overview,
                    data.voteAverage
                )
                localDataSource.updateTvShow(tvShow)
            }
        }.asLiveData()
    }

    override fun getFavoritesMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoritesTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, isFavorite: Boolean) {
        executor.diskIO().execute { localDataSource.setFavoriteMovie(movie, isFavorite) }
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, isFavorite: Boolean) {
        executor.diskIO().execute { localDataSource.setFavoriteTvShow(tvShow, isFavorite) }
    }
}