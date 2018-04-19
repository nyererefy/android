package com.konektedi.vs.Motions.Opinions;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.Utilities.Api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpinionsRepository {

    public MutableLiveData<List<OpinionsModel>> getOpinions(String motion_id) {

        final MutableLiveData<List<OpinionsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<OpinionsModel>> call = ApiUtilities.getOpinions().getOpinions(motion_id);

        call.enqueue(new Callback<List<OpinionsModel>>() {
            @Override
            public void onResponse(Call<List<OpinionsModel>> call, Response<List<OpinionsModel>> response) {
                if (response.isSuccessful()) {
                    List<OpinionsModel> electionsModelList = response.body();

                    listMutableLiveData.setValue(electionsModelList);

                }
            }

            @Override
            public void onFailure(Call<List<OpinionsModel>> call, Throwable t) {

            }

        });

        return listMutableLiveData;

    }
}