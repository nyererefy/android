package com.nyererefy.data.factories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nyererefy.data.source.OpinionsDataSource
import com.nyererefy.utilities.models.Opinion
import java.util.concurrent.Executor

class OpinionsDataFactory(private val executor: Executor, private val categoryId: Int) : DataSource.Factory<Int, Opinion>() {

    val mutableLiveData = MutableLiveData<OpinionsDataSource>()

    override fun create(): DataSource<Int, Opinion> {
        val dataSource = OpinionsDataSource(executor, categoryId)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}