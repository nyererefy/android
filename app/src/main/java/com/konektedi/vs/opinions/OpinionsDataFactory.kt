package com.konektedi.vs.opinions

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.konektedi.vs.utilities.models.Opinion
import java.util.concurrent.Executor

class OpinionsDataFactory(private val executor: Executor, private val categoryId: Int) : DataSource.Factory<Int, Opinion>() {

    val mutableLiveData = MutableLiveData<OpinionsDataSource>()

    override fun create(): DataSource<Int, Opinion> {
        val dataSource = OpinionsDataSource(executor,categoryId)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}