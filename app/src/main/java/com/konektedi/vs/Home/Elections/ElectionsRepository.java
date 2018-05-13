package com.konektedi.vs.Home.Elections;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.Utilities.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ElectionsRepository {

    public MutableLiveData<List<ElectionsModel>> getElections() {

        final MutableLiveData<List<ElectionsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<ElectionsModel>> call = ApiUtilities.getClient().getElections();

        call.enqueue(new Callback<List<ElectionsModel>>() {
            @Override
            public void onResponse(Call<List<ElectionsModel>> call, Response<List<ElectionsModel>> response) {
                if (response.isSuccessful()) {
                    listMutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ElectionsModel>> call, Throwable t) {
            }

        });

        return listMutableLiveData;
    }
}
