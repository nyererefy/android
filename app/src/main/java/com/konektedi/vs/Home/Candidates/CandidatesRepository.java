package com.konektedi.vs.Home.Candidates;


import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.R;
import com.konektedi.vs.Utilities.Api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.konektedi.vs.Home.Candidates.Candidates.getContextOfCandidates;

public class CandidatesRepository {

    public MutableLiveData<List<CandidatesModel>> getCandidates(String election_id, String category_id) {

        ((Candidates) getContextOfCandidates()).showProgressBar();

        final MutableLiveData<List<CandidatesModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<CandidatesModel>> call = ApiUtilities.getClient().getCandidates(election_id, category_id);

        call.enqueue(new Callback<List<CandidatesModel>>() {
            @Override
            public void onResponse(Call<List<CandidatesModel>> call, Response<List<CandidatesModel>> response) {
                ((Candidates) getContextOfCandidates()).hideProgressBar();

                if (response.isSuccessful()) {
                    List<CandidatesModel> categoriesModelList = response.body();
                    listMutableLiveData.setValue(categoriesModelList);

                } else if (response.code() == 404) {
                    ((Candidates) getContextOfCandidates()).showAlert(R.string.no_candidates);
                }
            }

            @Override
            public void onFailure(Call<List<CandidatesModel>> call, Throwable t) {
                ((Candidates) getContextOfCandidates()).hideProgressBar();
                ((Candidates) getContextOfCandidates()).showAlert(R.string.error);
            }
        });

        return listMutableLiveData;
    }
}

