package com.konektedi.vs.home.categories;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.utilities.NetworkStatus;
import com.konektedi.vs.utilities.api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRepository {

    public MutableLiveData<NetworkStatus> networkState = new MutableLiveData<>();

    public MutableLiveData<List<CategoriesModel>> getCategories(String election_id) {
        networkState.postValue(NetworkStatus.LOADING);

        final MutableLiveData<List<CategoriesModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<CategoriesModel>> call = ApiUtilities.getClient().getCategories(election_id);

        call.enqueue(new Callback<List<CategoriesModel>>() {
            @Override
            public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {
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
            public void onFailure(Call<List<CategoriesModel>> call, Throwable t) {
                networkState.postValue(NetworkStatus.FAILED);
            }

        });

        return listMutableLiveData;

    }
}
