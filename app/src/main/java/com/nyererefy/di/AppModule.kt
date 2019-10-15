package com.nyererefy.di

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.nyererefy.App.Companion.appContext
import com.nyererefy.BuildConfig.BASE_URL
import com.nyererefy.BuildConfig.WS_URL
import com.nyererefy.utilities.Pref
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun providePref(): Pref {
        return Pref(appContext)
    }

    @Singleton
    @Provides
    fun provideCookieJar(): PersistentCookieJar {
        return PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
    }

    @Singleton
    @Provides
    fun provideApolloClient(cookieJar: PersistentCookieJar): ApolloClient {
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
                        .cookieJar(cookieJar)
                        .build()

        val factory = WebSocketSubscriptionTransport.Factory(WS_URL, okHttpClient)

        return ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .subscriptionTransportFactory(factory)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetryExecutor(): Executor = Executors.newFixedThreadPool(5)

}
