package com.konektedi.vs.Motions;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class MotionsViewModel extends AndroidViewModel {

    private LiveData<List<MotionsModel>> motions;


    public MotionsViewModel(@NonNull Application application) {
        super(application);
        MotionsRepository motionsRepository = new MotionsRepository();
        motions = motionsRepository.getMotions();
    }

    LiveData<List<MotionsModel>> getAllMotions() {
        return motions;
    }


}

