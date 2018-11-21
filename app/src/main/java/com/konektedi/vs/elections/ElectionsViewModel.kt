package com.konektedi.vs.elections

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Election
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ElectionsViewModel : ViewModel() {
    var networkState: LiveData<NetworkState>
    private val executor: Executor
    private val tDataSource: LiveData<ElectionsDataSource>
    private val dataFactory: ElectionsDataFactory

    init {
        executor = Executors.newFixedThreadPool(5)
        dataFactory = ElectionsDataFactory(executor)

        tDataSource = dataFactory.mutableLiveData

        networkState = Transformations.switchMap(dataFactory.mutableLiveData) {
            it.networkState
        }
    }

    val elections: LiveData<PagedList<Election>>
        get() {
            val pagedListConfig = PagedList.Config.Builder()
                    .setEnablePlaceholders(true)
                    .setInitialLoadSizeHint(Constants.INITIAL_LOAD)
                    .setPageSize(Constants.PAGE_SIZE)
                    .build()

            return LivePagedListBuilder(dataFactory, pagedListConfig)
                    .setFetchExecutor(executor)
                    .build()
        }
}