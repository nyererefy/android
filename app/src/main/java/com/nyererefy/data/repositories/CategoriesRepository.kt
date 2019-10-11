package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoriesRepository
@Inject constructor(private val client: ApolloClient) {

//    fun fetchCategories(id: String): LiveData<Resource<CategoriesQuery.Data>> {
//        val query = CategoriesQuery.builder().electionId(id).build()
//        val elections = MutableLiveData<Resource<CategoriesQuery.Data>>()
//
//        elections.postValue(Resource(Status.LOADING))
//
//        client.query(query).enqueue(object : ApolloCall.Callback<CategoriesQuery.Data>() {
//            override fun onFailure(e: ApolloException) {
//                elections.postValue(Resource(Status.ERROR, null, e.localizedMessage))
//            }
//
//            override fun onResponse(response: Response<CategoriesQuery.Data>) {
//                when {
//                    response.hasErrors() -> {
//                        elections.postValue(Resource(Status.ERROR, null, response.errors()))
//                    }
//                    else -> elections.postValue(Resource(Status.SUCCESS, response.elections()))
//                }
//            }
//        })
//        return elections
//    }
}