package com.konektedi.vs.home.categories;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.konektedi.vs.utilities.NetworkStatus;

import java.util.List;


public class CategoriesViewModel extends AndroidViewModel {

    private LiveData<NetworkStatus> networkStatus;
    private LiveData<List<CategoriesModel>> categories;
    private CategoriesRepository repository;


    public CategoriesViewModel(@NonNull Application application) {
        super(application);
        repository = new CategoriesRepository();
        networkStatus = repository.networkState;
    }

    public LiveData<List<CategoriesModel>> getAllCategories(String election_id) {
        categories = repository.getCategories(election_id);
        return categories;
    }

    public LiveData<NetworkStatus> getNetworkStatus() {
        return networkStatus;
    }

}

