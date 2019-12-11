package com.nyererefy.di

import com.nyererefy.utilities.MessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class ServiceBuilderModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMessagingService(): MessagingService
}