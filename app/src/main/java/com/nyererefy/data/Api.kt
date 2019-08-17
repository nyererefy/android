package com.nyererefy.data

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.nyererefy.App.Companion.appContext
import com.nyererefy.ui.LoginActivity
import com.nyererefy.utilities.clearPreferences
import com.nyererefy.utilities.common.Constants
import com.nyererefy.utilities.common.Constants.BASE_URL
import com.nyererefy.utilities.common.Constants.CANDIDATE_ID
import com.nyererefy.utilities.common.Constants.CATEGORY_ID
import com.nyererefy.utilities.common.Constants.ELECTION_ID
import com.nyererefy.utilities.common.Constants.MOTION_ID
import com.nyererefy.utilities.common.Constants.OFFSET
import com.nyererefy.utilities.common.Constants.UNIVERSITY
import com.nyererefy.utilities.grabPreference
import com.nyererefy.utilities.models.*
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


interface Api {
    @GET("elections/elections")
    fun getElections(@Query(OFFSET) offset: Int): Call<List<Election>>

    @GET("categories/categories")
    fun getCategories(@Query(ELECTION_ID) election_id: Int): Call<List<Category>>

    @GET("elections/stats")
    fun getStats(@Query(ELECTION_ID) election_id: Int): Call<List<Stat>>

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
                   @Query(OFFSET) offset: Int): Call<List<Review>>

    @FormUrlEncoded
    @POST("reviews/review")
    fun postReview(@FieldMap map: Map<String, String>): Call<ResponseBody>

    @GET("news/posts")
    fun getNews(@Query(OFFSET) offset: Int): Call<List<Post>>

    @GET("motions/motions")
    fun getMotions(@Query(OFFSET) offset: Int): Call<List<Motion>>

    //Not the same base url
    @GET("updates/android")
    fun checkUpdate(): Call<AppUpdate>

    @GET("motions/motion")
    fun getMotion(@Query(MOTION_ID) motion_id: Int): Call<Motion>

    @FormUrlEncoded
    @POST("authentication/authenticate")
    fun authenticate(@FieldMap map: Map<String, String>): Call<User>

    @FormUrlEncoded
    @POST("votes/vote")
    fun vote(@FieldMap map: Map<String, String>): Call<ResponseBody>

    @POST("candidates/cover")
    fun postCover(@Body file: RequestBody): Call<ResponseBody>

    @FormUrlEncoded
    @POST("candidates/biography")
    fun postBio(@FieldMap map: Map<String, String>): Call<ResponseBody>

    @GET("motions/opinions")
    fun getOpinions(
            @Query(Constants.MOTION_ID) motion_id: Int,
            @Query(Constants.OFFSET) offset: Int): Call<List<Opinion>>

    @FormUrlEncoded
    @POST("motions/opinion")
    fun postOpinion(@FieldMap map: Map<String, String>): Call<ResponseBody>

//    @GET("news/comments/{post_id}/{offset}")
//    fun getComments(@Path("post_id") post_id: Int, @Path(OFFSET) offset: Int): Call<List<Comments>>

    @FormUrlEncoded
    @POST("news/comment")
    fun postComment(@FieldMap map: Map<String, String>): Call<ResponseBody>

    companion object {
        /*   Being naked means we don't add identity parameters in the header */
        fun create(naked: Boolean = false, baseUrl: String = BASE_URL): Api {
            val okHttpClient = OkHttpClient.Builder()

            okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
            okHttpClient.readTimeout(30, TimeUnit.SECONDS)
            okHttpClient.writeTimeout(30, TimeUnit.SECONDS)
            okHttpClient.retryOnConnectionFailure(true)

            okHttpClient.addInterceptor { chain ->
                val request = chain.request()

                val session = request.newBuilder()

                session.addHeader(Constants.DEVICE, deviceName)
                session.addHeader(Constants.ANDROID_ID, getAndroidID())

                if (!naked) {
                    session.addHeader(Constants.ID, grabPreference(appContext, Constants.ID))
                    session.addHeader(Constants.UNIVERSITY, grabPreference(appContext, UNIVERSITY))
                    session.addHeader(Constants.TOKEN, grabPreference(appContext, Constants.TOKEN))
                }

                chain.proceed(session.build())
            }

            okHttpClient.addInterceptor { chain ->
                val request = chain.request()
                val response = chain.proceed(request)

                when (response.code()) {
                    401 -> {
                        clearPreferences(appContext)

                        val i = Intent(appContext, LoginActivity::class.java)
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        appContext.startActivity(i)
                    }
                }

                response
            }

            return Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Api::class.java)
        }

        private val deviceName: String
            get() = ("${Build.MANUFACTURER} ${Build.MODEL} ${Build.VERSION.RELEASE} ${Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name}")

        @SuppressLint("HardwareIds")
        private fun getAndroidID(): String = Settings.Secure.getString(appContext.contentResolver,
                Settings.Secure.ANDROID_ID)

    }
}