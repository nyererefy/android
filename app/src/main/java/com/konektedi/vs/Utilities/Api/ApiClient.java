package com.konektedi.vs.Utilities.Api;

import android.content.Context;

import com.konektedi.vs.MainActivity;
import com.konektedi.vs.Student.StudentPreferences;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.konektedi.vs.Utilities.Constants.BRANCH;
import static com.konektedi.vs.Utilities.Constants.ID;
import static com.konektedi.vs.Utilities.Constants.RESIDENCE;
import static com.konektedi.vs.Utilities.Constants.SCHOOL;
import static com.konektedi.vs.Utilities.Constants.SEX;
import static com.konektedi.vs.Utilities.Constants.UNIVERSITY;
import static com.konektedi.vs.Utilities.Constants.X_API_KEY;
import static com.konektedi.vs.Utilities.Constants.X_API_KEY_VALUE;
import static com.konektedi.vs.Utilities.Constants.YEAR;

/**
 * Created by Sy on 4/9/2018.
 */

public class ApiClient {

    private static Retrofit retrofit = null;

    static Retrofit getClient(String base_url) {

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        okHttpClient.connectTimeout(60, TimeUnit.SECONDS);
        okHttpClient.readTimeout(60, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(60, TimeUnit.SECONDS);
        okHttpClient.retryOnConnectionFailure(true);

        okHttpClient.addInterceptor(chain -> {
            Request request = chain.request();
            Context applicationContext = MainActivity.getContextOfApplication();


            Request.Builder session = request.newBuilder()
                    //No underscores.....
                    .addHeader(ID, StudentPreferences.getPreference(applicationContext, ID))
                    .addHeader(UNIVERSITY, StudentPreferences.getPreference(applicationContext, UNIVERSITY))
                    .addHeader(BRANCH, StudentPreferences.getPreference(applicationContext, BRANCH))
                    .addHeader(SCHOOL, StudentPreferences.getPreference(applicationContext, SCHOOL))
                    .addHeader(YEAR, StudentPreferences.getPreference(applicationContext, YEAR))
                    .addHeader(RESIDENCE, StudentPreferences.getPreference(applicationContext, RESIDENCE))
                    .addHeader(SEX, StudentPreferences.getPreference(applicationContext, SEX))
                    .addHeader(X_API_KEY, X_API_KEY_VALUE);

            return chain.proceed(session.build());
        });

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
