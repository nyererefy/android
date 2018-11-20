package com.konektedi.vs.utilities.api;


import com.konektedi.vs.motions.MotionsModel;
import com.konektedi.vs.motions.opinions.Opinions;
import com.konektedi.vs.news.comments.Comments;

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
    @FormUrlEncoded
    @POST("votes/vote")
    Call<ResponseBody> vote(@FieldMap Map<String, String> map);

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