package com.konektedi.vs.motions;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.konektedi.vs.utilities.NetworkStatus;

import java.util.List;


public class MotionsViewModel extends AndroidViewModel {

    private LiveData<List<MotionsModel>> motions;
    private LiveData<NetworkStatus> networkStatus;



    public MotionsViewModel(@NonNull Application application) {
        super(application);
        MotionsRepository repository = new MotionsRepository();
        motions = repository.getMotions();
        networkStatus = repository.networkState;

    }

    LiveData<List<MotionsModel>> getAllMotions() {
        return motions;
    }

    LiveData<NetworkStatus> getNetworkStatus() {
        return networkStatus;
    }


}

