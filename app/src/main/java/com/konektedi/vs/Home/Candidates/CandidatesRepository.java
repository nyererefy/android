package com.konektedi.vs.Home.Candidates;


import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.konektedi.vs.Utilities.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidatesRepository {

    public MutableLiveData<List<CandidatesModel>> getCandidates(String election_id, String category_id) {

        final MutableLiveData<List<CandidatesModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<CandidatesModel>> call = ApiUtilities.getClient().getCandidates(election_id, category_id);

        call.enqueue(new Callback<List<CandidatesModel>>() {
            @Override
            public void onResponse(Call<List<CandidatesModel>> call, Response<List<CandidatesModel>> response) {

                if (response.isSuccessful()) {

                    List<CandidatesModel> categoriesModelList = response.body();

                    Log.d("cand res", response.toString());
                    listMutableLiveData.setValue(categoriesModelList);

                }

            }

            @Override
            public void onFailure(Call<List<CandidatesModel>> call, Throwable t) {
                Log.d("cand err", t.toString());

            }

        });

        return listMutableLiveData;

    }
}

