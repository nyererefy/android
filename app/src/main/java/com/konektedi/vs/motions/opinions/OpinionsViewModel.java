package com.konektedi.vs.motions.opinions;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.konektedi.vs.utilities.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OpinionsViewModel extends ViewModel {

    public LiveData<PagedList<Opinions>> postList;
    public LiveData<NetworkState> networkState;
    Executor executor;
    LiveData<OpinionsDataSource> tDataSource;

    public OpinionsViewModel() {
    }

    public LiveData<PagedList<Opinions>> getPostList(int motion_id) {

        executor = Executors.newFixedThreadPool(5);
        OpinionsDataFactory dataFactory = new OpinionsDataFactory(executor, motion_id);

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