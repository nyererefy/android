package com.konektedi.vs.motions;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.utilities.NetworkStatus;
import com.konektedi.vs.utilities.api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotionsRepository {
    public MutableLiveData<NetworkStatus> networkState = new MutableLiveData<>();

    public MutableLiveData<List<MotionsModel>> getMotions() {
        networkState.postValue(NetworkStatus.LOADING);

        final MutableLiveData<List<MotionsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<MotionsModel>> call = ApiUtilities.getClient().getMotions();

        call.enqueue(new Callback<List<MotionsModel>>() {
            @Override
            public void onResponse(Call<List<MotionsModel>> call, Response<List<MotionsModel>> response) {
                if (response.isSuccessful()) {
                    networkState.postValue(NetworkStatus.LOADED);
                    listMutableLiveData.setValue(response.body());
                } else {
                    networkState.postValue(NetworkStatus.ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<MotionsModel>> call, Throwable t) {
                networkState.postValue(NetworkStatus.FAILED);
            }

        });

        return listMutableLiveData;

    }
}

