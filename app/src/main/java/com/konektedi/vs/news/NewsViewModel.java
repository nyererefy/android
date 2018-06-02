package com.konektedi.vs.news;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private LiveData<List<NewsModel>> news;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        NewsRepository newsRepository = new NewsRepository();
        news = newsRepository.getNews();
    }

    LiveData<List<NewsModel>> getAllNews() {
        return news;
    }
}
