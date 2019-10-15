package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository
@Inject constructor(private val client: ApolloClient) {

}