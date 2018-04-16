package com.konektedi.vs.Motions.Opinions;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class OpinionsModelView extends AndroidViewModel {

    private LiveData<List<OpinionsModel>> opinions;
    private String motion_id;


    public OpinionsModelView(@NonNull Application application, String motion_id) {
        super(application);

        OpinionsRepository opinionsRepository = new OpinionsRepository(motion_id);
        opinions = opinionsRepository.getMotions();
    }

    LiveData<List<OpinionsModel>> getAllOpinions(String motion_id) {
        setMotion_id(motion_id);
        return opinions;
    }


    public String getMotion_id() {
        return motion_id;
    }

    public void setMotion_id(String motion_id) {
        this.motion_id = motion_id;
    }
}