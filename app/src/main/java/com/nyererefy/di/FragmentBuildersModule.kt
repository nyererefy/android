package com.nyererefy.di


import com.nyererefy.ui.fragments.*

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
    abstract fun contributeCategoriesFragment(): SubcategoriesFragment

    @ContributesAndroidInjector
    abstract fun contributeCandidatesFragment(): CandidatesFragment

    @ContributesAndroidInjector
    abstract fun contributeCandidateProfileFragment(): CandidateProfileFragment

    @ContributesAndroidInjector
    abstract fun contributeConfirmClassFragment(): ConfirmClassFragment

    @ContributesAndroidInjector
    abstract fun contributeSetInfoFragment(): SetInfoFragment
}
