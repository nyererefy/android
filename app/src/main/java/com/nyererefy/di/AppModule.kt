package com.nyererefy.di

import com.apollographql.apollo.ApolloClient
import com.nyererefy.utilities.common.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideApolloClient(): ApolloClient {
        val okHttpClient =
                OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)

                        .addInterceptor { chain ->
                            val request = chain.request()

                            val session = request.newBuilder()

                            session.addHeader(Constants.DEVICE, "")

                            chain.proceed(session.build())
                        }
                        .build()

        return ApolloClient.builder()
                .serverUrl("https://3333.com")
                .okHttpClient(okHttpClient)
                .build()
    }
}
