package com.nyererefy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nyererefy.graphql.ElectionsQuery
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.Status
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElectionsRepository
@Inject constructor(private val client: ApolloClient) {

    fun fetchElections(): LiveData<Resource<ElectionsQuery.Data>> {
        val query = ElectionsQuery.builder().build()
        val data = MutableLiveData<Resource<ElectionsQuery.Data>>()

        data.postValue(Resource(Status.LOADING))

        client.query(query).enqueue(object : ApolloCall.Callback<ElectionsQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                data.postValue(Resource(Status.ERROR, null, e.localizedMessage))
            }

            override fun onResponse(response: Response<ElectionsQuery.Data>) {
                if (response.hasErrors()) return
                data.postValue(Resource(Status.SUCCESS, response.data()))
            }
        })
        return data
    }
}