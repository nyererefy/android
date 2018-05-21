package com.konektedi.vs.Motions.Opinions;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import java.util.concurrent.Executor;

public class OpinionsDataFactory extends DataSource.Factory<Integer, Opinions> {

    MutableLiveData<OpinionsDataSource> mutableLiveData;
    OpinionsDataSource dataSource;
    Executor executor;
    private int motion_id;


    public OpinionsDataFactory(Executor executor, int motion_id) {
        this.mutableLiveData = new MutableLiveData<>();
        this.executor = executor;
        this.motion_id = motion_id;
    }


    @Override
    public DataSource create() {
        dataSource = new OpinionsDataSource(executor, motion_id);
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<OpinionsDataSource> getMutableLiveData() {
        return mutableLiveData;
    }

}