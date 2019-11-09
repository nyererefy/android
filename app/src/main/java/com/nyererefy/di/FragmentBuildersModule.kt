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
    abstract fun contributeCountsFragment(): CountsFragment

    @ContributesAndroidInjector
    abstract fun contributeCountsAndReviewsFragment(): CountsAndReviewsFragment

    @ContributesAndroidInjector
    abstract fun contributeAddReviewFragment(): AddReviewFragment

    @ContributesAndroidInjector
    abstract fun contributeReviewsFragment(): ReviewsFragment

    @ContributesAndroidInjector
    abstract fun contributeContributorsFragment(): ContributorsFragment

    @ContributesAndroidInjector
    abstract fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector
    abstract fun contributeChangeDetailsFragment(): ChangeDetailsFragment
}
