package com.nyererefy.data.repositories

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nyererefy.graphql.ConfirmDataMutation
import com.nyererefy.graphql.SetupAccountMutation
import com.nyererefy.graphql.type.UserSetupInput
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.NetworkState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository
@Inject constructor(private val client: ApolloClient) {

    fun confirmData(): MutableLiveData<NetworkState> {
        val mutation = ConfirmDataMutation.builder().build()

        val networkState = MutableLiveData<NetworkState>()
        networkState.value = NetworkState.LOADING

        client.mutate(mutation).enqueue(object : ApolloCall.Callback<ConfirmDataMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                networkState.postValue(NetworkState.error(e.localizedMessage))
            }

            override fun onResponse(response: Response<ConfirmDataMutation.Data>) {
                when {
                    response.hasErrors() -> {
                        networkState.postValue(NetworkState.error(response.errors()[0].message()))
                    }
                    else -> {
                        val isDataConfirmed = response.data()?.confirmData()?.isDataConfirmed

                        if (isDataConfirmed != null && isDataConfirmed == true) {
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.error("Failed to confirm"))
                        }
                    }
                }
            }
        })

        return networkState
    }

    fun setupAccount(input: UserSetupInput): Resource<SetupAccountMutation.Data> {
        val mutation = SetupAccountMutation.builder().input(input).build()

        val networkState = MutableLiveData<NetworkState>()
        val data = MutableLiveData<SetupAccountMutation.Data>()

        networkState.value = NetworkState.LOADING

        client.mutate(mutation).enqueue(object : ApolloCall.Callback<SetupAccountMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                networkState.postValue(NetworkState.error(e.localizedMessage))
            }

            override fun onResponse(response: Response<SetupAccountMutation.Data>) {
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

}