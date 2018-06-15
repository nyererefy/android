package com.konektedi.vs.home.elections;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.utilities.NetworkStatus;
import com.konektedi.vs.utilities.api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ElectionsRepository {

    public MutableLiveData<NetworkStatus> networkState = new MutableLiveData<>();

    public MutableLiveData<List<ElectionsModel>> getElections() {

        networkState.postValue(NetworkStatus.LOADING);

        final MutableLiveData<List<ElectionsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<ElectionsModel>> call = ApiUtilities.getClient().getElections();

        call.enqueue(new Callback<List<ElectionsModel>>() {
            @Override
            public void onResponse(Call<List<ElectionsModel>> call, Response<List<ElectionsModel>> response) {

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
            public void onFailure(Call<List<ElectionsModel>> call, Throwable t) {
                networkState.postValue(NetworkStatus.FAILED);
            }
        });

        return listMutableLiveData;
    }
}
