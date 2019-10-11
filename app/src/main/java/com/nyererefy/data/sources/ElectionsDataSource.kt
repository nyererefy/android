package com.nyererefy.data.sources

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nyererefy.graphql.ElectionsQuery
import com.nyererefy.utilities.common.NetworkState
import timber.log.Timber
import java.util.concurrent.Executor


class ElectionsDataSource(
        private val api: ApolloClient,
        private val retryExecutor: Executor,
        private val query: String
) : PageKeyedDataSource<Int, ElectionsQuery.Election>() {

    private var retry: (() -> Any)? = null

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
            callback: LoadCallback<Int, ElectionsQuery.Election>) {
        // ignored, since we only ever append to our initial load
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ElectionsQuery.Election>) {
        networkState.postValue(NetworkState.LOADING)

        val req = api.query(
                ElectionsQuery
                        .builder()
                        .offset(params.key)
                        .query(query)
                        .build()
        )

        req.enqueue(object : ApolloCall.Callback<ElectionsQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                retry = {
                    loadAfter(params, callback)
                }
                networkState.postValue(NetworkState.error(e.message ?: "unknown err"))
            }

            override fun onResponse(response: Response<ElectionsQuery.Data>) {
                if (!response.hasErrors()) {
                    val elections = response.data()?.elections() ?: emptyList()
                    retry = null
                    callback.onResult(elections, params.key + 10)

                    if (elections.isEmpty()) {
                        networkState.postValue(NetworkState.END)
                    } else {
                        networkState.postValue(NetworkState.LOADED)
                    }
                } else {
                    retry = {
                        loadAfter(params, callback)
                    }
                    networkState.postValue(
                            NetworkState.error("error code: ${response.errors()}")
                    )
                }
            }

        })
    }

    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, ElectionsQuery.Election>) {

        val request = api.query(
                ElectionsQuery
                        .builder()
                        .query(query)
                        .build()
        )

        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        request.enqueue(object : ApolloCall.Callback<ElectionsQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                retry = {
                    loadInitial(params, callback)
                }
                val error = NetworkState.error(e.message ?: "unknown error")
                networkState.postValue(error)
                initialLoad.postValue(error)
            }

            override fun onResponse(response: Response<ElectionsQuery.Data>) {
                val elections = response.data()?.elections() ?: emptyList()

                retry = null
                if (elections.isEmpty()) {
                    networkState.postValue(NetworkState.EMPTY)
                } else {
                    networkState.postValue(NetworkState.LOADED)
                }
                initialLoad.postValue(NetworkState.LOADED)
                callback.onResult(elections, 0, 10)
            }
        })
    }
}