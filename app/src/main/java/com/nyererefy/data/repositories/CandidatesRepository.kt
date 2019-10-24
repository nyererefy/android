package com.nyererefy.data.repositories

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.ApolloSubscriptionCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nyererefy.graphql.*
import com.nyererefy.graphql.type.CandidateEditInput
import com.nyererefy.graphql.type.VoteInput
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.NetworkState
import com.nyererefy.utilities.common.invokeMutation
import com.nyererefy.utilities.common.invokeQuery
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CandidatesRepository
@Inject constructor(private val client: ApolloClient) {

    fun fetchCandidates(id: String): Resource<CandidatesQuery.Data> {
        val query = CandidatesQuery.builder().subcategoryId(id.toInt()).build()

        return invokeQuery<CandidatesQuery.Data>(client.query(query))
    }

    fun fetchCandidate(id: String): Resource<CandidateQuery.Data> {
        val query = CandidateQuery.builder().id(id.toInt()).build()

        return invokeQuery<CandidateQuery.Data>(client.query(query))
    }

    fun fetchCandidatesAndVotes(id: String): Resource<CountsQuery.Data> {
        val query = CountsQuery.builder().subcategoryId(id.toInt()).build()

        return invokeQuery<CountsQuery.Data>(client.query(query))
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

    fun submitVote(input: VoteInput): Resource<CreateVoteMutation.Data> {
        val mutation = CreateVoteMutation.builder().input(input).build()

        return invokeMutation<CreateVoteMutation.Data>(client.mutate(mutation))
    }

    fun editCandidate(input: CandidateEditInput): Resource<UpdateCandidateMutation.Data> {
        val mutation = UpdateCandidateMutation.builder().input(input).build()

        return invokeMutation<UpdateCandidateMutation.Data>(client.mutate(mutation))
    }
}