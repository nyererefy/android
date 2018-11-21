package com.konektedi.vs.opinions

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Result
import com.konektedi.vs.utilities.models.Opinion
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class OpinionsViewModel : ViewModel() {
    lateinit var networkState: LiveData<NetworkState>
    private lateinit var executor: Executor
    private lateinit var tDataSource: LiveData<OpinionsDataSource>
    private lateinit var dataFactory: OpinionsDataFactory

    fun getOpinions(categoryId: Int): LiveData<PagedList<Opinion>> {
        executor = Executors.newFixedThreadPool(5)
        dataFactory = OpinionsDataFactory(executor, categoryId)

        tDataSource = dataFactory.mutableLiveData

        networkState = Transformations.switchMap(dataFactory.mutableLiveData) {
            it.networkState
        }
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