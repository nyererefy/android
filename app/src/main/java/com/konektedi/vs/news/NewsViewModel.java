package com.konektedi.vs.news;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.konektedi.vs.utilities.NetworkStatus;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private LiveData<List<NewsModel>> news;
    private LiveData<NetworkStatus> networkStatus;


    public NewsViewModel(@NonNull Application application) {
        super(application);
        NewsRepository repository = new NewsRepository();
        news = repository.getNews();
        networkStatus = repository.networkState;
    }

    LiveData<List<NewsModel>> getAllNews() {
        return news;
    }

    LiveData<NetworkStatus> getNetworkStatus() {
        return networkStatus;
    }
}
