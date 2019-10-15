package com.nyererefy.data.repositories

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nyererefy.graphql.LoginMutation
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.NetworkState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository
@Inject constructor(private val client: ApolloClient) {
    fun submitGoogleToken(token: String): Resource<LoginMutation.Data> {
        val networkState = MutableLiveData<NetworkState>()

        val data = MutableLiveData<LoginMutation.Data>()
        networkState.value = NetworkState.LOADING

        val mute = LoginMutation.builder().token(token).build()

        client.mutate(mute).enqueue(object : ApolloCall.Callback<LoginMutation.Data>() {
            override fun onFailure(e: ApolloException) {
                networkState.postValue(NetworkState.error(e.localizedMessage))
            }

            override fun onResponse(response: Response<LoginMutation.Data>) = when {
                response.hasErrors() -> networkState.postValue(NetworkState.error(response.errors()[0].message()))
                else -> {
                    networkState.postValue(NetworkState.LOADED)
                    data.postValue(response.data())
                }
            }
        })
        return Resource(data, networkState)
    }
}