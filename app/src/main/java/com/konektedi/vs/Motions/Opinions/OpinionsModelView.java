package com.konektedi.vs.Motions.Opinions;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class OpinionsModelView extends AndroidViewModel {

    private LiveData<List<OpinionsModel>> opinions;
    private OpinionsRepository opinionsRepository = new OpinionsRepository();

    public OpinionsModelView(@NonNull Application application) {
        super(application);
    }

    LiveData<List<OpinionsModel>> getAllOpinions(String motion_id) {
        opinions = opinionsRepository.getOpinions(motion_id);
        return opinions;
    }

}