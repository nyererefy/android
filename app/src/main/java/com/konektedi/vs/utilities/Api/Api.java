package com.konektedi.vs.utilities.Api;


import com.konektedi.vs.home.candidates.CandidatesModel;
import com.konektedi.vs.home.categories.CategoriesModel;
import com.konektedi.vs.home.elections.ElectionsModel;
import com.konektedi.vs.motions.MotionsModel;
import com.konektedi.vs.motions.opinions.Opinions;
import com.konektedi.vs.news.Comments.Comments;
import com.konektedi.vs.news.NewsModel;
import com.konektedi.vs.student.StudentModel;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @Headers("X-API-KEY: oNQ6r&mv#j|m]u")
    @GET("authentication/authentication/{reg_no}/{password}")
    Call<StudentModel> authenticate(
            @Path("reg_no") String reg_no,
            @Path("password") String password);

    @GET("elections/elections")
    Call<List<ElectionsModel>> getElections();

    @GET("categories/categories/{election_id}")
    Call<List<CategoriesModel>> getCategories(
            @Path("election_id") String election_id);

    @GET("candidates/candidates/{election_id}/{category_id}")
    Call<List<CandidatesModel>> getCandidates(
            @Path("election_id") String election_id,
            @Path("category_id") String category_id);

    @FormUrlEncoded
    @POST("votes/vote")
    Call<ResponseBody> vote(@FieldMap Map<String, String> map);

    @GET("news/posts")
    Call<List<NewsModel>> getNews();

    @GET("motions/motions")
    Call<List<MotionsModel>> getMotions();

    @GET("motions/opinions/{motion_id}/{offset}")
    Call<List<Opinions>> getOpinions(
            @Path("motion_id") int motion_id, @Path("offset") int offset);

    @FormUrlEncoded
    @POST("motions/opinion")
    Call<ResponseBody> postOpinion(@FieldMap Map<String, String> map);

    @GET("news/comments/{post_id}/{offset}")
    Call<List<Comments>> getComments(@Path("post_id") int post_id, @Path("offset") int offset);

    @FormUrlEncoded
    @POST("news/comment")
    Call<ResponseBody> postComment(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("settings/password")
    Call<ResponseBody> changePassword(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("settings/username")
    Call<ResponseBody> changeUsername(@FieldMap Map<String, String> map);

}