package com.nyererefy.data.factories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.apollographql.apollo.ApolloClient
import com.nyererefy.data.sources.ElectionsDataSource
import com.nyererefy.graphql.ElectionsQuery
import java.util.concurrent.Executor


/**
 * A simple source factory which also provides a way to observe the last created elections source.
 * This allows us to channel its elections requests status etc back to the UI.
 */
class ElectionsDataFactory(
        private val api: ApolloClient,
        private val retryExecutor: Executor,
        private val query: String
) :
        DataSource.Factory<Int, ElectionsQuery.Election>() {

    val sourceLiveData = MutableLiveData<ElectionsDataSource>()

    override fun create(): DataSource<Int, ElectionsQuery.Election> {

        val source = ElectionsDataSource(api, retryExecutor, query)
        sourceLiveData.postValue(source)
        return source
    }

}