package com.nyererefy.data.factories

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nyererefy.data.source.ElectionsDataSource
import com.nyererefy.utilities.models.Election
import java.util.concurrent.Executor

class ElectionsDataFactory(private val executor: Executor) : DataSource.Factory<Int, Election>() {

    val mutableLiveData = MutableLiveData<ElectionsDataSource>()

    override fun create(): DataSource<Int, Election> {
        val dataSource = ElectionsDataSource(executor)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}