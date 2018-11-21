package com.konektedi.vs.comments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.konektedi.vs.utilities.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CommentsViewModel extends ViewModel {
    public LiveData<PagedList<Comments>> postList;
    public LiveData<NetworkState> networkState;
    Executor executor;
    LiveData<CommentsDataSource> tDataSource;

    public CommentsViewModel() {
    }

    public LiveData<PagedList<Comments>> getPostList(int post_id) {

        executor = Executors.newFixedThreadPool(5);
        CommentsDataFactory dataFactory = new CommentsDataFactory(executor, post_id);

        tDataSource = dataFactory.getMutableLiveData();

        networkState = Transformations.switchMap(dataFactory.getMutableLiveData(), dataSource
                -> dataSource.getNetworkState());

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        postList = (new LivePagedListBuilder(dataFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();

        return postList;
    }
}
