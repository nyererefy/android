package com.konektedi.vs.Home.Elections;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.MainActivity;
import com.konektedi.vs.Utilities.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.konektedi.vs.MainActivity.getContextOfMainActivity;

public class ElectionsRepository {

    public MutableLiveData<List<ElectionsModel>> getElections() {

        ((MainActivity) getContextOfMainActivity()).showProgressBar();

        final MutableLiveData<List<ElectionsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<ElectionsModel>> call = ApiUtilities.getClient().getElections();

        call.enqueue(new Callback<List<ElectionsModel>>() {
            @Override
            public void onResponse(Call<List<ElectionsModel>> call, Response<List<ElectionsModel>> response) {
                ((MainActivity) getContextOfMainActivity()).hideProgressBar();

                if (response.isSuccessful()) {
                    List<ElectionsModel> electionsModelList = response.body();

                    listMutableLiveData.setValue(electionsModelList);
                }
            }

            @Override
            public void onFailure(Call<List<ElectionsModel>> call, Throwable t) {
                ((MainActivity) getContextOfMainActivity()).hideProgressBar();
            }

        });

        return listMutableLiveData;
    }
}
