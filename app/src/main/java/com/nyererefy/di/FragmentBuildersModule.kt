package com.nyererefy.di


import com.nyererefy.ui.fragments.CategoriesFragment
import com.nyererefy.ui.fragments.ElectionsFragment
import com.nyererefy.ui.fragments.SurveysFragment
import com.nyererefy.ui.fragments.UserFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeElectionsFragment(): ElectionsFragment

    @ContributesAndroidInjector
    abstract fun contributeUserFragment(): UserFragment

    @ContributesAndroidInjector
    abstract fun contributeSurveysFragment(): SurveysFragment

    @ContributesAndroidInjector
    abstract fun contributeCategoriesFragment(): CategoriesFragment
}
