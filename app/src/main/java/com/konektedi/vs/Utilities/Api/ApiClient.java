package com.konektedi.vs.Utilities.Api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sy on 4/9/2018.
 */

public class ApiClient {


    private static Retrofit retrofit = null;

    static Retrofit getClient(String base_url) {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder session = request.newBuilder()
                        .addHeader("secret-key", "BHH")

                        .addHeader("id", "1")
                        .addHeader("university_id", "1")
                        .addHeader("branch_id", "1")
                        .addHeader("school_id", "1")
                        .addHeader("year", "1")
                        .addHeader("residence_id", "1")
                        .addHeader("sex", "M")
                        .addHeader("dp", "");

                return chain.proceed(session.build());
            }
        });

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .client(okHttpBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
