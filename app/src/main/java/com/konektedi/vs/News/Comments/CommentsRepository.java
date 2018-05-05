package com.konektedi.vs.News.Comments;

import android.arch.lifecycle.MutableLiveData;

import com.konektedi.vs.Utilities.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsRepository {

    public MutableLiveData<List<CommentsModel>> getComments(String post_id) {

        final MutableLiveData<List<CommentsModel>> listMutableLiveData = new MutableLiveData<>();

        Call<List<CommentsModel>> call = ApiUtilities.getClient().getComments(post_id);

        call.enqueue(new Callback<List<CommentsModel>>() {
            @Override
            public void onResponse(Call<List<CommentsModel>> call, Response<List<CommentsModel>> response) {
                if (response.isSuccessful()) {
                    List<CommentsModel> commentsModelList = response.body();

                    listMutableLiveData.setValue(commentsModelList);

                }
            }

            @Override
            public void onFailure(Call<List<CommentsModel>> call, Throwable t) {

            }

        });

        return listMutableLiveData;

    }
}
