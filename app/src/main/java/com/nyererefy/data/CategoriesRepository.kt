package com.nyererefy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.nyererefy.graphql.CategoriesQuery
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.Status
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepository
@Inject constructor(private val client: ApolloClient) {

    fun fetchCategories(id: String): LiveData<Resource<CategoriesQuery.Data>> {
        val query = CategoriesQuery.builder().electionId(id).build()
        val data = MutableLiveData<Resource<CategoriesQuery.Data>>()

        data.postValue(Resource(Status.LOADING))

        client.query(query).enqueue(object : ApolloCall.Callback<CategoriesQuery.Data>() {
            override fun onFailure(e: ApolloException) {
                data.postValue(Resource(Status.ERROR, null, e.localizedMessage))
            }

            override fun onResponse(response: Response<CategoriesQuery.Data>) {
                when {
                    response.hasErrors() -> {
                        data.postValue(Resource(Status.ERROR, null, response.errors()))
                    }
                    else -> data.postValue(Resource(Status.SUCCESS, response.data()))
                }
            }
        })
        return data
    }
}