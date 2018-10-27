package com.konektedi.vs.home.reviews

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PageKeyedDataSource
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.api.Api
import com.konektedi.vs.utilities.api.ApiN
import com.konektedi.vs.utilities.models.Review
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Executor

class ReviewsDataSource(private val retryExecutor: Executor, private val categoryId: Int) : PageKeyedDataSource<Int, Review>() {

    // keep a function reference for the retry event
    private var retry: (() -> Any)? = null
    private val apiClient = ApiN.create()

    /**
     * There is no sync on the state because paging will always call loadInitial first then wait
     * for it to return some success value before calling loadAfter.
     */
    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, Review>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Review>) {
        networkState.postValue(NetworkState.LOADING)

        apiClient.getReviews(categoryId, params.key).enqueue(
                object : retrofit2.Callback<List<Review>> {
                    override fun onFailure(call: Call<List<Review>>, t: Throwable) {
                        retry = {
                            loadAfter(params, callback)
                        }
                        networkState.postValue(NetworkState.error(t.message ?: "unknown err"))
                    }

                    override fun onResponse(
                            call: Call<List<Review>>,
                            response: Response<List<Review>>) {
                        when {
                            response.isSuccessful -> {
                                retry = null
                                callback.onResult(response.body() as MutableList<Review>, params.key + 10)
                                networkState.postValue(NetworkState.LOADED)
                            }
                            response.code() == 404 -> networkState.postValue(NetworkState.END)
                            else -> {
                                retry = {
                                    loadAfter(params, callback)
                                }
                                networkState.postValue(
                                        NetworkState.error("error code: ${response.code()}"))
                            }
                        }
                    }
                }
        )
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Review>) {

        val request = apiClient.getReviews(categoryId, 0)
        initialLoad.postValue(NetworkState.LOADING)

        // triggered by a refresh, we better execute sync
        try {
            retry = null
            val response = request.execute()

            when {
                response.isSuccessful -> {
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(response.body() as MutableList<Review>, 0, 10)
                }
                response.code() == 404 -> networkState.postValue(NetworkState.END)
                else -> networkState.postValue(
                        NetworkState.error("error code: ${response.code()}"))
            }

        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
            val error = NetworkState.error(ioException.message ?: "unknown error")
            networkState.postValue(error)
            initialLoad.postValue(error)
        }
    }
}