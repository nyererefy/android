package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import com.nyererefy.graphql.CreateReviewMutation
import com.nyererefy.graphql.ReviewsQuery
import com.nyererefy.graphql.type.ReviewInput
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.invokeMutation
import com.nyererefy.utilities.common.invokeQuery
import com.nyererefy.viewmodels.ReviewsArgs
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewsRepository
@Inject constructor(private val client: ApolloClient) {

    fun postReview(input: ReviewInput): Resource<CreateReviewMutation.Data> {
        val mutation = CreateReviewMutation.builder().input(input).build()

        return invokeMutation<CreateReviewMutation.Data>(client.mutate(mutation))
    }

    fun fetchReviews(args: ReviewsArgs): Resource<ReviewsQuery.Data> {
        val query = ReviewsQuery.builder()
                .subcategoryId(args.subcategoryId)
                .offset(args.offset)
                .build()

        return invokeQuery<ReviewsQuery.Data>(client.query(query))
    }
}