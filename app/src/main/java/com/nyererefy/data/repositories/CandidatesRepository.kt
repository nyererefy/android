package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import com.nyererefy.graphql.CandidatesQuery
import com.nyererefy.graphql.CreateVoteMutation
import com.nyererefy.graphql.type.VoteInput
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.invokeMutation
import com.nyererefy.utilities.common.invokeQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CandidatesRepository
@Inject constructor(private val client: ApolloClient) {

    fun fetchCandidates(id: String): Resource<CandidatesQuery.Data> {
        val query = CandidatesQuery.builder().subcategoryId(id.toInt()).build()

        return invokeQuery<CandidatesQuery.Data>(client.query(query))
    }

    fun submitVote(input: VoteInput): Resource<CreateVoteMutation.Data> {
        val mutation = CreateVoteMutation.builder().input(input).build()

        return invokeMutation<CreateVoteMutation.Data>(client.mutate(mutation))
    }
}