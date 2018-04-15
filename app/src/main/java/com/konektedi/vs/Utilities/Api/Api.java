package com.konektedi.vs.Utilities.Api;


import com.konektedi.vs.Home.Candidates.CandidatesModel;
import com.konektedi.vs.Home.Candidates.NuxModel;
import com.konektedi.vs.Home.Categories.CategoriesModel;
import com.konektedi.vs.Home.Elections.ElectionsModel;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("elections/elections")
    Call<List<ElectionsModel>> getElections();

    @GET("categories/categories/{election_id}")
    Call<List<CategoriesModel>> getCategories(@Path("election_id") String election_id);

    @GET("candidates/vote_check/{election_id}/{category_id}")
    Call<NuxModel> checkIfYouHaveVoted(@Path("election_id") String election_id, @Path("category_id") String category_id);

    @GET("candidates/candidates/{category_id}")
    Call<List<CandidatesModel>> getCandidates(@Path("category_id") String category_id);

    @FormUrlEncoded
    @POST("candidates/vote")
    Call<ResponseBody> vote(@FieldMap Map<String, String> map);

}