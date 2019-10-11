package com.nyererefy.data.repositories

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import com.apollographql.apollo.ApolloClient
import com.nyererefy.data.factories.ElectionsDataFactory
import com.nyererefy.graphql.ElectionsQuery
import com.nyererefy.utilities.Listing
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElectionsRepository
@Inject constructor(
        private val client: ApolloClient,
        private val retryExecutor: Executor
) {
    @MainThread
    fun fetchElections(query: String): Listing<ElectionsQuery.Election> {

        val sourceFactory = ElectionsDataFactory(client, retryExecutor, query)

        val livePagedList = LivePagedListBuilder(sourceFactory, 5)
                .setFetchExecutor(retryExecutor)
                .build()

        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
            it.initialLoad
        }

        return Listing(
                pagedList = livePagedList,
                networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
                    it.networkState
                },
                retry = {
                    sourceFactory.sourceLiveData.value?.retryAllFailed()
                },
                refresh = {
                    sourceFactory.sourceLiveData.value?.invalidate()
                },
                refreshState = refreshState
        )
    }
}