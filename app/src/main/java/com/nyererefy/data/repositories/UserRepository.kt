package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import com.nyererefy.graphql.*
import com.nyererefy.graphql.type.FirebaseTokenInput
import com.nyererefy.graphql.type.UserSetupInput
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.invokeMutation
import com.nyererefy.utilities.common.invokeQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository
@Inject constructor(private val client: ApolloClient) {

    fun confirmData(): Resource<ConfirmDataMutation.Data> {
        val mutation = ConfirmDataMutation.builder().build()

        return invokeMutation<ConfirmDataMutation.Data>(client.mutate(mutation))
    }

    fun setupAccount(input: UserSetupInput): Resource<SetupAccountMutation.Data> {
        val mutation = SetupAccountMutation.builder().input(input).build()

        return invokeMutation<SetupAccountMutation.Data>(client.mutate(mutation))
    }

    fun me(): Resource<MeQuery.Data> {
        val query = MeQuery.builder().build()

        return invokeQuery<MeQuery.Data>(client.query(query))
    }

    fun fetchClassInfo(): Resource<ClassInfoQuery.Data> {
        val query = ClassInfoQuery.builder().build()

        return invokeQuery<ClassInfoQuery.Data>(client.query(query))
    }

    fun sendFirebaseToken(input: FirebaseTokenInput): Resource<CreateFirebaseTokenMutation.Data> {
        val mutation = CreateFirebaseTokenMutation.builder().input(input).build()

        return invokeMutation<CreateFirebaseTokenMutation.Data>(client.mutate(mutation))
    }

}