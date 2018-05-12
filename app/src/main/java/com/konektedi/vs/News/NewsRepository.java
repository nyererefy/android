package com.konektedi.vs.News;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.MainActivity;
import com.konektedi.vs.Utilities.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.konektedi.vs.MainActivity.getContextOfMainActivity;

public class NewsRepository {


    public MutableLiveData<List<NewsModel>> getNews() {

        ((MainActivity) getContextOfMainActivity()).showProgressBar();

        final MutableLiveData<List<NewsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<NewsModel>> call = ApiUtilities.getClient().getNews();

        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                ((MainActivity) getContextOfMainActivity()).hideProgressBar();

                if (response.isSuccessful()) {
                    List<NewsModel> newsModelList = response.body();

                    listMutableLiveData.setValue(newsModelList);

                }

            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                ((MainActivity) getContextOfMainActivity()).hideProgressBar();
            }

        });

        return listMutableLiveData;

    }
}
