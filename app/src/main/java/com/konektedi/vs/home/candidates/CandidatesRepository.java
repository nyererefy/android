package com.konektedi.vs.home.candidates;


import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.utilities.NetworkStatus;
import com.konektedi.vs.utilities.api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidatesRepository {
    public MutableLiveData<NetworkStatus> networkState = new MutableLiveData<>();

    public MutableLiveData<List<CandidatesModel>> getCandidates(String election_id, String category_id) {
        networkState.postValue(NetworkStatus.LOADING);

        final MutableLiveData<List<CandidatesModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<CandidatesModel>> call = ApiUtilities.getClient().getCandidates(election_id, category_id);

        call.enqueue(new Callback<List<CandidatesModel>>() {
            @Override
            public void onResponse(Call<List<CandidatesModel>> call, Response<List<CandidatesModel>> response) {

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
            public void onFailure(Call<List<CandidatesModel>> call, Throwable t) {
                networkState.postValue(NetworkStatus.FAILED);
            }
        });

        return listMutableLiveData;
    }
}

