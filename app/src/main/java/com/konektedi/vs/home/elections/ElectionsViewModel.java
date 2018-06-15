package com.konektedi.vs.home.elections;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.konektedi.vs.utilities.NetworkStatus;

import java.util.List;

public class ElectionsViewModel extends AndroidViewModel {

    private LiveData<List<ElectionsModel>> elections;
    private LiveData<NetworkStatus> networkStatus;

    public ElectionsViewModel(@NonNull Application application) {
        super(application);
        ElectionsRepository repository = new ElectionsRepository();
        elections = repository.getElections();

        networkStatus = repository.networkState;
    }

    LiveData<List<ElectionsModel>> getAllElections() {
        return elections;
    }

    LiveData<NetworkStatus> getNetworkStatus() {
        return networkStatus;
    }
}
