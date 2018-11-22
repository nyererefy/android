package com.konektedi.vs.news

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.konektedi.vs.utilities.models.Election
import com.konektedi.vs.utilities.models.Post
import java.util.concurrent.Executor

class NewsDataFactory(private val executor: Executor) : DataSource.Factory<Int, Post>() {

    val mutableLiveData = MutableLiveData<NewsDataSource>()

    override fun create(): DataSource<Int, Post> {
        val dataSource = NewsDataSource(executor)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}