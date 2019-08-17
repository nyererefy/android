package com.nyererefy.data.factories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nyererefy.data.source.MotionsDataSource
import com.nyererefy.utilities.models.Motion
import java.util.concurrent.Executor

class MotionsDataFactory(private val executor: Executor) : DataSource.Factory<Int, Motion>() {

    val mutableLiveData = MutableLiveData<MotionsDataSource>()

    override fun create(): DataSource<Int, Motion> {
        val dataSource = MotionsDataSource(executor)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}