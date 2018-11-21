package com.konektedi.vs.motions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Motion
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MotionsViewModel : ViewModel() {
    var networkState: LiveData<NetworkState>
    private val executor: Executor
    private val tDataSource: LiveData<MotionsDataSource>
    private val dataFactory: MotionsDataFactory
    private val repository = MotionsRepository()
    var repoNetworkState: LiveData<NetworkState>

    init {
        executor = Executors.newFixedThreadPool(5)
        dataFactory = MotionsDataFactory(executor)

        tDataSource = dataFactory.mutableLiveData

        networkState = Transformations.switchMap(dataFactory.mutableLiveData) {
            it.networkState
        }

        repoNetworkState = repository.mNetworkState
    }

    val motions: LiveData<PagedList<Motion>>
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

    fun getMotion(motionId: Int): LiveData<Motion> {
        return repository.getMotion(motionId)
    }

    fun submitOpinion(map: Map<String, String>): MutableLiveData<NetworkState> {
        return repository.submit(map)
    }
}