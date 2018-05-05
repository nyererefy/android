package com.konektedi.vs.News;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.Utilities.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    public NewsRepository() {
    }

    public MutableLiveData<List<NewsModel>> getNews() {

        final MutableLiveData<List<NewsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<NewsModel>> call = ApiUtilities.getClient().getNews();

        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (response.isSuccessful()) {
                    List<NewsModel> newsModelList = response.body();

                    listMutableLiveData.setValue(newsModelList);

                }

            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {

            }

        });

        return listMutableLiveData;

    }
}
