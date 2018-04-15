package com.konektedi.vs.Home.Elections;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class ElectionsViewModel extends AndroidViewModel {

    private LiveData<List<ElectionsModel>> elections;

    public ElectionsViewModel(@NonNull Application application) {
        super(application);
        ElectionsRepository electionsRepository = new ElectionsRepository();
        elections = electionsRepository.getElections();
    }

    LiveData<List<ElectionsModel>> getAllElections() {
        return elections;
    }
}
