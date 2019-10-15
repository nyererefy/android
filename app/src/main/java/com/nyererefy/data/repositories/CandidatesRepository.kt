package com.nyererefy.data.repositories

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloSubscriptionCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nyererefy.graphql.*
import com.nyererefy.graphql.type.CandidateEditInput
import com.nyererefy.graphql.type.VoteInput
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.NetworkState
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CandidatesRepository
@Inject constructor(private val client: ApolloClient) {

    fun fetchCandidates(id: String): Resource<CandidatesQuery.Data> {
        val query = CandidatesQuery.builder().subcategoryId(id.toInt()).build()

        val networkState = MutableLiveData<NetworkState>()
        val data = MutableLiveData<CandidatesQuery.Data>()

        networkState.value = NetworkState.LOADING

        client.query(query).enqueue(object : ApolloCall.Callback<CandidatesQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                networkState.postValue(NetworkState.error(e.localizedMessage))
            }

            override fun onResponse(response: Response<CandidatesQuery.Data>) {
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

    fun fetchCandidate(id: String): Resource<CandidateQuery.Data> {
        val query = CandidateQuery.builder().id(id.toInt()).build()

        val networkState = MutableLiveData<NetworkState>()
        val data = MutableLiveData<CandidateQuery.Data>()

        networkState.value = NetworkState.LOADING

        client.query(query).enqueue(object : ApolloCall.Callback<CandidateQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                networkState.postValue(NetworkState.error(e.localizedMessage))
            }

            override fun onResponse(response: Response<CandidateQuery.Data>) {
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

    fun subscribeCandidate(id: String): Resource<CandidateSubscription.Data> {
        val subscription = CandidateSubscription.builder().id(id.toInt()).build()

        val networkState = MutableLiveData<NetworkState>()
        val data = MutableLiveData<CandidateSubscription.Data>()

        networkState.value = NetworkState.LOADING

        client.subscribe(subscription).execute(
                object : ApolloSubscriptionCall.Callback<CandidateSubscription.Data> {
                    override fun onResponse(response: Response<CandidateSubscription.Data>) {
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

    fun submitVote(input: VoteInput): MutableLiveData<NetworkState> {
        val mutation = CreateVoteMutation.builder().input(input).build()

        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING

        client.mutate(mutation).enqueue(object : ApolloCall.Callback<CreateVoteMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                networkState.postValue(NetworkState.error(e.localizedMessage))
            }

            override fun onResponse(response: Response<CreateVoteMutation.Data>) {
                when {
                    response.hasErrors() -> {
                        networkState.postValue(NetworkState.error(response.errors()[0].message()))
                    }
                    else -> networkState.postValue(NetworkState.LOADED)
                }
            }
        })
        return networkState
    }

    fun editCandidate(input: CandidateEditInput): MutableLiveData<NetworkState> {
        val mutation = UpdateCandidateMutation.builder().input(input).build()

        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING

        client.mutate(mutation).enqueue(object : ApolloCall.Callback<UpdateCandidateMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                networkState.postValue(NetworkState.error(e.localizedMessage))
            }

            override fun onResponse(response: Response<UpdateCandidateMutation.Data>) {
                when {
                    response.hasErrors() -> {
                        networkState.postValue(NetworkState.error(response.errors()[0].message()))
                    }
                    else -> networkState.postValue(NetworkState.LOADED)
                }
            }
        })
        return networkState
    }
}