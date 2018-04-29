package com.konektedi.vs.Home.Categories;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.Utilities.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesRepository {

    public MutableLiveData<List<CategoriesModel>> getCategories(String election_id) {

        final MutableLiveData<List<CategoriesModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<CategoriesModel>> call = ApiUtilities.getCategories().getCategories(election_id);

        call.enqueue(new Callback<List<CategoriesModel>>() {
            @Override
            public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {
                if (response.isSuccessful()) {
                    List<CategoriesModel> categoriesModelList = response.body();

                    listMutableLiveData.setValue(categoriesModelList);

                }

            }

            @Override
            public void onFailure(Call<List<CategoriesModel>> call, Throwable t) {

            }

        });

        return listMutableLiveData;

    }
}
