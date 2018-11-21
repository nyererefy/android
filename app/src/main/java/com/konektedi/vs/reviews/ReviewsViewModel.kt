package com.konektedi.vs.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
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

    fun refresh(categoryId: Int) {
        repo.getVotes(categoryId)
    }

    fun submitReview(map: Map<String, String>): LiveData<NetworkState> {
        return repo.postReview(map)
    }

}