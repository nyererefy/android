package com.konektedi.vs.home.reviews

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Result
import com.konektedi.vs.utilities.models.Review
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ReviewsViewModel : ViewModel() {
    lateinit var pagedNetworkState: LiveData<NetworkState>

    private lateinit var executor: Executor
    private lateinit var tDataSource: LiveData<ReviewsDataSource>
    private lateinit var dataFactory: ReviewsDataFactory

    //From paged
    fun getReviews(categoryId: Int): LiveData<PagedList<Review>> {
        executor = Executors.newFixedThreadPool(5)
        dataFactory = ReviewsDataFactory(executor, categoryId)

        tDataSource = dataFactory.mutableLiveData

        pagedNetworkState = Transformations.switchMap(dataFactory.mutableLiveData) {
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

    //Repo
    private val repo = ReviewsRepository()
    val mNetworkState = repo.mNetworkState

    fun getVotes(categoryId: Int): LiveData<List<Result>> {
        return repo.getVotes(categoryId)
    }

    fun submitReview(map: Map<String, String>): LiveData<NetworkState> {
        return repo.postReview(map)
    }

}