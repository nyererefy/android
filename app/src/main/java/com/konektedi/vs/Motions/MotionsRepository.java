package com.konektedi.vs.Motions;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.MainActivity;
import com.konektedi.vs.Utilities.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.konektedi.vs.MainActivity.getContextOfMainActivity;

public class MotionsRepository {

    public MutableLiveData<List<MotionsModel>> getMotions() {
        ((MainActivity) getContextOfMainActivity()).showProgressBar();


        final MutableLiveData<List<MotionsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<MotionsModel>> call = ApiUtilities.getClient().getMotions();

        call.enqueue(new Callback<List<MotionsModel>>() {
            @Override
            public void onResponse(Call<List<MotionsModel>> call, Response<List<MotionsModel>> response) {
                ((MainActivity) getContextOfMainActivity()).hideProgressBar();

                if (response.isSuccessful()) {
                    List<MotionsModel> electionsModelList = response.body();

                    listMutableLiveData.setValue(electionsModelList);

                }

            }

            @Override
            public void onFailure(Call<List<MotionsModel>> call, Throwable t) {
                ((MainActivity) getContextOfMainActivity()).hideProgressBar();
            }

        });

        return listMutableLiveData;

    }
}

