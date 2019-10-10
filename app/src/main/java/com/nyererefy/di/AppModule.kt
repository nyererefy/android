package com.nyererefy.di

import com.apollographql.apollo.ApolloClient
import com.nyererefy.BuildConfig.BASE_URL
import com.nyererefy.utilities.common.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient {
        //Logger
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient =
                OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .addInterceptor(logger)
                        .addInterceptor {
                            val request = it.request()
                            val builder = request.newBuilder()

                            //Adding things to request...
                            builder.addHeader(Constants.DEVICE, "")

                            //Then proceed...
                            it.proceed(builder.build())
                        }
                        .build()

        return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build()
    }
}
