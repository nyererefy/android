package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import com.nyererefy.graphql.CandidateSubscription
import com.nyererefy.graphql.CreateReviewMutation
import com.nyererefy.graphql.ReviewSubscription
import com.nyererefy.graphql.ReviewsQuery
import com.nyererefy.graphql.type.ReviewInput
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.invokeMutation
import com.nyererefy.utilities.common.invokeQuery
import com.nyererefy.utilities.common.invokeSubscription
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

    fun subscribeToReviews(subcategoryId: Int): Resource<ReviewSubscription.Data> {
        val subscription = ReviewSubscription.builder().subcategoryId(subcategoryId).build()

        return invokeSubscription<ReviewSubscription.Data>(client.subscribe(subscription))
    }
}