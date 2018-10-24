package com.konektedi.vs.home.elections

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.konektedi.vs.utilities.models.Election
import java.util.concurrent.Executor

class ElectionsDataFactory(private val executor: Executor) : DataSource.Factory<Int, Election>() {

    val mutableLiveData = MutableLiveData<ElectionsDataSource>()

    override fun create(): DataSource<Int, Election>? {
        val dataSource = ElectionsDataSource(executor)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}