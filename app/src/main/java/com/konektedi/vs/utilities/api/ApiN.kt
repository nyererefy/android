package com.konektedi.vs.utilities.api

import android.util.Log
import com.konektedi.vs.MainActivity
import com.konektedi.vs.comments.Comments
import com.konektedi.vs.student.grabPreference
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.Constants.CANDIDATE_ID
import com.konektedi.vs.utilities.common.Constants.CATEGORY_ID
import com.konektedi.vs.utilities.common.Constants.ELECTION_ID
import com.konektedi.vs.utilities.common.Constants.MOTION_ID
import com.konektedi.vs.utilities.common.Constants.UNIVERSITY
import com.konektedi.vs.utilities.common.Constants.X_API_KEY
import com.konektedi.vs.utilities.common.Constants.X_API_KEY_VALUE
import com.konektedi.vs.utilities.models.*
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiN {
    @GET("elections/elections")
    fun getElections(@Query("offset") offset: Int): Call<List<Election>>

    @GET("categories/categories")
    fun getCategories(
            @Query(ELECTION_ID) election_id: Int): Call<List<Category>>

    @GET("elections/stats")
    fun getStats(
            @Query(ELECTION_ID) election_id: Int): Call<List<Stat>>

    @GET("candidates/candidates")
    fun getCandidates(
            @Query(ELECTION_ID) election_id: Int,
            @Query(CATEGORY_ID) category_id: Int): Call<Listing>

    @GET("candidates/candidate")
    fun getCandidate(@Query(CANDIDATE_ID) candidate_id: Int): Call<CandidateProfile>

    @GET("results/results")
    fun getResult(@Query(CATEGORY_ID) category_id: Int): Call<List<Result>>

    @GET("reviews/reviews")
    fun getReviews(@Query(CATEGORY_ID) category_id: Int,
                   @Query("offset") offset: Int): Call<List<Review>>

    @FormUrlEncoded
    @POST("reviews/review")
    fun postReview(@FieldMap map: Map<String, String>): Call<ResponseBody>

    @GET("news/posts")
    fun getNews(@Query("offset") offset: Int): Call<List<Post>>

    @GET("motions/motions")
    fun getMotions(@Query("offset") offset: Int): Call<List<Motion>>

    @GET("motions/motion")
    fun getMotion(@Query(MOTION_ID) motion_id: Int): Call<Motion>

    @Headers("X-API-KEY: oNQ6r&mv#j|m]u")
    @FormUrlEncoded
    @POST("authentication/authenticate")
    fun authenticate(@FieldMap map: Map<String, String>): Call<User>


    @FormUrlEncoded
    @POST("votes/vote")
    fun vote(@FieldMap map: Map<String, String>): Call<ResponseBody>

    @POST("candidates/cover")
    fun postCover(@Body file: RequestBody): Call<ResponseBody>

    @GET("motions/opinions")
    fun getOpinions(
            @Query(Constants.MOTION_ID) motion_id: Int,
            @Query(Constants.OFFSET) offset: Int): Call<List<Opinion>>

    @FormUrlEncoded
    @POST("motions/opinion")
    fun postOpinion(@FieldMap map: Map<String, String>): Call<ResponseBody>

    @GET("news/comments/{post_id}/{offset}")
    fun getComments(@Path("post_id") post_id: Int, @Path("offset") offset: Int): Call<List<Comments>>

    @FormUrlEncoded
    @POST("news/comment")
    fun postComment(@FieldMap map: Map<String, String>): Call<ResponseBody>

    @FormUrlEncoded
    @POST("settings/password")
    fun changePassword(@FieldMap map: Map<String, String>): Call<ResponseBody>

    @FormUrlEncoded
    @POST("settings/username")
    fun changeUsername(@FieldMap map: Map<String, String>): Call<ResponseBody>


    companion object {
        //        private const val BASE_URL = "http://vs.konektedi.com/api/v1/"
        private const val BASE_URL = "http://192.168.43.228/konektedi_vs/api/v1/"

        fun create(): ApiN {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("URL", it)
            })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val okHttpClient = OkHttpClient.Builder()

            okHttpClient.addInterceptor { chain ->
                val request = chain.request()
                val applicationContext = MainActivity.contextOfApplication

                val session = request.newBuilder()
                        .addHeader(Constants.ID, grabPreference(applicationContext, Constants.ID))
                        .addHeader(Constants.UNIVERSITY, grabPreference(applicationContext, UNIVERSITY))
                        .addHeader(Constants.X_API_KEY, X_API_KEY_VALUE)

                chain.proceed(session.build())
            }

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiN::class.java)
        }

        //Used only when login
        fun subClient(): ApiN {
            val okHttpClient = OkHttpClient.Builder()

            okHttpClient.addInterceptor { chain ->
                val request = chain.request()

                val session = request.newBuilder()
                        .addHeader(X_API_KEY, X_API_KEY_VALUE)

                chain.proceed(session.build())
            }

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiN::class.java)
        }
    }
}