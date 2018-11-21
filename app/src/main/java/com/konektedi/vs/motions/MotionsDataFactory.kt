package com.konektedi.vs.motions

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.konektedi.vs.utilities.models.Election
import com.konektedi.vs.utilities.models.Motion
import java.util.concurrent.Executor

class MotionsDataFactory(private val executor: Executor) : DataSource.Factory<Int, Motion>() {

    val mutableLiveData = MutableLiveData<MotionsDataSource>()

    override fun create(): DataSource<Int, Motion> {
        val dataSource = MotionsDataSource(executor)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}