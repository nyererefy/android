package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import com.nyererefy.graphql.*
import com.nyererefy.graphql.type.CandidateEditInput
import com.nyererefy.graphql.type.VoteInput
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.invokeMutation
import com.nyererefy.utilities.common.invokeQuery
import com.nyererefy.utilities.common.invokeSubscription
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

    fun subscribeToCandidatesAndVotes(id: String): Resource<CountsSubscription.Data> {
        val subscription = CountsSubscription.builder().subcategoryId(id.toInt()).build()

        return invokeSubscription<CountsSubscription.Data>(client.subscribe(subscription))
    }

    fun subscribeCandidate(id: String): Resource<CandidateSubscription.Data> {
        val subscription = CandidateSubscription.builder().id(id.toInt()).build()

        return invokeSubscription<CandidateSubscription.Data>(client.subscribe(subscription))
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