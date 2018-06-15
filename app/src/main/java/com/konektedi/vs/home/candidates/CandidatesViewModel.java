package com.konektedi.vs.home.candidates;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.konektedi.vs.utilities.NetworkStatus;

import java.util.List;


public class CandidatesViewModel extends AndroidViewModel {

    private LiveData<List<CandidatesModel>> categories;
    private LiveData<NetworkStatus> networkStatus;
    private CandidatesRepository repository = new CandidatesRepository();


    public CandidatesViewModel(@NonNull Application application) {
        super(application);
        networkStatus = repository.networkState;

    }

    public LiveData<List<CandidatesModel>> getAllCandidates(String election_id,String category_id) {
        categories = repository.getCandidates(election_id,category_id);
        return categories;
    }

    LiveData<NetworkStatus> getNetworkStatus() {
        return networkStatus;
    }


}




