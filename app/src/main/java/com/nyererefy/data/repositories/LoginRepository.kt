package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import com.nyererefy.graphql.LoginMutation
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.invokeMutation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository
@Inject constructor(private val client: ApolloClient) {
    fun submitGoogleToken(token: String): Resource<LoginMutation.Data> {
        val mute = LoginMutation.builder().token(token).build()

        return invokeMutation<LoginMutation.Data>(client.mutate(mute))
    }
}