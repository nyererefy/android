package com.konektedi.vs.home.candidates;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class CandidatesViewModel extends AndroidViewModel {

    private LiveData<List<CandidatesModel>> categories;
    private CandidatesRepository candidatesRepository = new CandidatesRepository();


    public CandidatesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CandidatesModel>> getAllCandidates(String election_id,String category_id) {
        categories = candidatesRepository.getCandidates(election_id,category_id);
        return categories;
    }

}




