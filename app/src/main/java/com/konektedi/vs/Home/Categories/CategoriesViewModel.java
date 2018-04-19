package com.konektedi.vs.Home.Categories;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;


public class CategoriesViewModel extends AndroidViewModel {

    private LiveData<List<CategoriesModel>> categories;
    private CategoriesRepository categoriesRepository = new CategoriesRepository();


    public CategoriesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CategoriesModel>> getAllCategories(String election_id) {
        categories = categoriesRepository.getCategories(election_id);
        return categories;
    }
}

