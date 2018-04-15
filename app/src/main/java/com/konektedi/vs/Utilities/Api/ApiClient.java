package com.konektedi.vs.Utilities.Api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sy on 4/9/2018.
 */

public class ApiClient {

    private static Retrofit retrofit = null;
    private static Retrofit.Builder retrofitBuilder = null;

    static Retrofit getClient(String base_url) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    static Retrofit postClient(String base_url) {
        if (retrofitBuilder == null) {
            OkHttpClient okHttpClient = new OkHttpClient();

            retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient);
            retrofit = retrofitBuilder.build();
        }

        return retrofit;
    }

}
