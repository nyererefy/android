package com.konektedi.vs.comments;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import java.util.concurrent.Executor;

public class CommentsDataFactory extends DataSource.Factory<Integer, Comments> {

    MutableLiveData<CommentsDataSource> mutableLiveData;
    CommentsDataSource dataSource;
    Executor executor;
    private int post_id;


    public CommentsDataFactory(Executor executor, int post_id) {
        this.mutableLiveData = new MutableLiveData<>();
        this.executor = executor;
        this.post_id = post_id;
    }


    @Override
    public DataSource create() {
        dataSource = new CommentsDataSource(executor, post_id);
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<CommentsDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

}