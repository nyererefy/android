package com.konektedi.vs.news;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.utilities.NetworkStatus;
import com.konektedi.vs.utilities.api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    public MutableLiveData<NetworkStatus> networkState = new MutableLiveData<>();

    public MutableLiveData<List<NewsModel>> getNews() {
        networkState.postValue(NetworkStatus.LOADING);

        final MutableLiveData<List<NewsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<NewsModel>> call = ApiUtilities.getClient().getNews();

        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {

                networkState.postValue(NetworkStatus.LOADED);

                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(response.body());
                } else if (response.code() == 404) {
                    networkState.postValue(NetworkStatus.NOTHING);
                } else {
                    networkState.postValue(NetworkStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                networkState.postValue(NetworkStatus.FAILED);
            }

        });

        return listMutableLiveData;

    }
}
