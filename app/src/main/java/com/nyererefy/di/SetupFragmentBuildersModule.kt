package com.nyererefy.di


import com.nyererefy.ui.fragments.ConfirmClassFragment
import com.nyererefy.ui.fragments.SetInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class SetupFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeConfirmClassFragment(): ConfirmClassFragment

    @ContributesAndroidInjector
    abstract fun contributeSetInfoFragment(): SetInfoFragment
}
