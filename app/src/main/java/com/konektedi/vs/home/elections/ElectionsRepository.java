package com.konektedi.vs.home.elections;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.utilities.api.ApiUtilities;
import com.konektedi.vs.utilities.NetworkState;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ElectionsRepository {

    ElectionsRepository() {
        networkState = new MutableLiveData<>();
    }

    private MutableLiveData<NetworkState> networkState;

    public MutableLiveData<List<ElectionsModel>> getElections() {

        networkState.postValue(NetworkState.LOADING);

        final MutableLiveData<List<ElectionsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<ElectionsModel>> call = ApiUtilities.getClient().getElections();

        call.enqueue(new Callback<List<ElectionsModel>>() {
            @Override
            public void onResponse(Call<List<ElectionsModel>> call, Response<List<ElectionsModel>> response) {
                networkState.postValue(NetworkState.LOADED);

                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ElectionsModel>> call, Throwable t) {
                networkState.postValue(NetworkState.FAILED);
            }
        });

        return listMutableLiveData;
    }
}
