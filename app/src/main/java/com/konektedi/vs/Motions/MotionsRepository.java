package com.konektedi.vs.Motions;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.Utilities.Api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MotionsRepository {

    public MutableLiveData<List<MotionsModel>> getMotions() {

        final MutableLiveData<List<MotionsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<MotionsModel>> call = ApiUtilities.getClient().getMotions();

        call.enqueue(new Callback<List<MotionsModel>>() {
            @Override
            public void onResponse(Call<List<MotionsModel>> call, Response<List<MotionsModel>> response) {

                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<MotionsModel>> call, Throwable t) {
            }

        });

        return listMutableLiveData;

    }
}

