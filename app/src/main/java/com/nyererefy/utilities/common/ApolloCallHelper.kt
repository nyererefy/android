package com.nyererefy.utilities.common

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloMutationCall
import com.apollographql.apollo.ApolloQueryCall
import com.apollographql.apollo.ApolloSubscriptionCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nyererefy.graphql.CandidateSubscription
import com.nyererefy.utilities.Resource
import timber.log.Timber

/**
 * Used for abstracting all queries.
 */
fun <T : Any> invokeQuery(apolloQueryCall: ApolloQueryCall<T>): Resource<T> {
    val networkState = MutableLiveData<NetworkState>()
    val data = MutableLiveData<T>()

    networkState.value = NetworkState.LOADING

    apolloQueryCall.enqueue(object : ApolloCall.Callback<T>() {
        override fun onFailure(e: ApolloException) {
            networkState.postValue(NetworkState.error(e.localizedMessage))
        }

        override fun onResponse(response: Response<T>) {
            when {
                response.hasErrors() -> {
                    networkState.postValue(NetworkState.error(response.errors()[0].message()))
                }
                else -> {
                    networkState.postValue(NetworkState.LOADED)
                    data.postValue(response.data())
                }
            }
        }
    })

    return Resource(data, networkState)
}

/**
 * Used for abstracting all mutations.
 */
fun <T : Any> invokeMutation(apolloMutationCall: ApolloMutationCall<T>): Resource<T> {
    val networkState = MutableLiveData<NetworkState>()
    val data = MutableLiveData<T>()

    networkState.value = NetworkState.LOADING

    apolloMutationCall.enqueue(object : ApolloCall.Callback<T>() {
        override fun onFailure(e: ApolloException) {
            networkState.postValue(NetworkState.error(e.localizedMessage))
        }

        override fun onResponse(response: Response<T>) {
            when {
                response.hasErrors() -> {
                    networkState.postValue(NetworkState.error(response.errors()[0].message()))
                }
                else -> {
                    networkState.postValue(NetworkState.LOADED)
                    data.postValue(response.data())
                }
            }
        }
    })

    return Resource(data, networkState)
}


fun <T : Any> invokeSubscription(apolloSubscriptionCall: ApolloSubscriptionCall<T>): Resource<T> {
    val networkState = MutableLiveData<NetworkState>()
    val data = MutableLiveData<T>()

    networkState.value = NetworkState.LOADING

    apolloSubscriptionCall.execute(
            object : ApolloSubscriptionCall.Callback<T> {
                override fun onResponse(response: Response<T>) {
                    Timber.d("onResponse")

                    when {
                        response.hasErrors() -> {
                            networkState.postValue(NetworkState.error(response.errors()[0].message()))
                        }
                        else -> {
                            networkState.postValue(NetworkState.LOADED)
                            data.postValue(response.data())
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                    Timber.e(e.message)
                    networkState.postValue(NetworkState.error(e.localizedMessage))
                }

                override fun onConnected() {
                    Timber.d("Connected")
                }

                override fun onTerminated() {
                    Timber.d("Terminated")
                }

                override fun onCompleted() {
                    Timber.d("Completed")
                }
            })

    return Resource(data, networkState)
}

