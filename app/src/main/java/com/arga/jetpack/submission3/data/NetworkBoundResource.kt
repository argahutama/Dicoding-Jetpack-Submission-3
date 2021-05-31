package com.arga.jetpack.submission3.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.arga.jetpack.submission3.data.source.remote.ApiResponse
import com.arga.jetpack.submission3.data.source.remote.StatusResponse
import com.arga.jetpack.submission3.util.AppExecutor
import com.arga.jetpack.submission3.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val executors: AppExecutor) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    private fun onFetchFailed() {}

    protected abstract fun loadDataFromDB(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType): Boolean?
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>?
    protected abstract fun saveCallResult(data: RequestType)

    @Suppress("LeakingThis")
    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse!!) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response.status) {
                StatusResponse.SUCCESS -> executors.diskIO().execute {

                    response.body?.let { saveCallResult(it) }

                    executors.mainThread().execute {
                        result.addSource(
                            loadDataFromDB()
                        ) { newData -> result.setValue(Resource.success(newData)) }
                    }
                }

                StatusResponse.EMPTY -> executors.mainThread().execute {
                    result.addSource(
                        loadDataFromDB()
                    ) { newData -> result.setValue(Resource.success(newData)) }
                }

                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(
                        dbSource
                    ) { newData ->
                        result.setValue(response.message?.let {
                            Resource.error(
                                it,
                                newData
                            )
                        })
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result

    init {
        result.value = Resource.loading(null)

        val dbSource = loadDataFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)!!) fetchFromNetwork(dbSource)
            else result.addSource(dbSource) { newData ->
                result.setValue(Resource.success(newData))
            }
        }
    }
}