package com.nyererefy.di

import com.nyererefy.ui.LoginActivity
import com.nyererefy.ui.MainActivity
import com.nyererefy.ui.SetupActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [SetupFragmentBuildersModule::class])
    abstract fun contributeSetupActivity(): SetupActivity
}
