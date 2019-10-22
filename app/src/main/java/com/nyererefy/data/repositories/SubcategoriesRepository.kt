package com.nyererefy.data.repositories

import com.apollographql.apollo.ApolloClient
import com.nyererefy.graphql.SubcategoriesQuery
import com.nyererefy.utilities.Resource
import com.nyererefy.utilities.common.invokeQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubcategoriesRepository
@Inject constructor(private val client: ApolloClient) {

    fun fetchCategories(id: String): Resource<SubcategoriesQuery.Data> {
        val query = SubcategoriesQuery.builder().electionId(id.toInt()).build()

        return invokeQuery<SubcategoriesQuery.Data>(client.query(query))
    }
}