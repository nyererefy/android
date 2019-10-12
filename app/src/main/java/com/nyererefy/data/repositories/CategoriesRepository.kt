package com.nyererefy.data.repositories

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nyererefy.graphql.SubcategoriesQuery
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.NetworkState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepository
@Inject constructor(private val client: ApolloClient) {

    fun fetchCategories(id: String): Resource<SubcategoriesQuery.Data> {
        val query = SubcategoriesQuery.builder().electionId(id.toInt()).build()

        val networkState = MutableLiveData<NetworkState>()
        val data = MutableLiveData<SubcategoriesQuery.Data>()

        networkState.value = NetworkState.LOADING

        client.query(query).enqueue(object : ApolloCall.Callback<SubcategoriesQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                networkState.postValue(NetworkState.error(e.localizedMessage))
            }

            override fun onResponse(response: Response<SubcategoriesQuery.Data>) {
                when {
                    response.hasErrors() -> {
                        networkState.postValue(NetworkState.error(response.errors().toString()))
                    }
                    else -> data.postValue(response.data())
                }
            }
        })
        return Resource(data, networkState)
    }
}