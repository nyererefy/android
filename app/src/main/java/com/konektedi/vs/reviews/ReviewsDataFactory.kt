package com.konektedi.vs.reviews

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.konektedi.vs.utilities.models.Review
import java.util.concurrent.Executor

class ReviewsDataFactory(private val executor: Executor, private val categoryId: Int) : DataSource.Factory<Int, Review>() {

    val mutableLiveData = MutableLiveData<ReviewsDataSource>()

    override fun create(): DataSource<Int, Review> {
        val dataSource = ReviewsDataSource(executor,categoryId)
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}