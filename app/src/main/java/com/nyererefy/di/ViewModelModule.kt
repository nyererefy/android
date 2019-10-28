package com.nyererefy.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nyererefy.viewmodels.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ElectionsViewModel::class)
    abstract fun bindUserViewModel(viewModel: ElectionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubcategoriesViewModel::class)
    abstract fun bindSubcategoriesViewModel(viewModel: SubcategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CandidatesViewModel::class)
    abstract fun bindCandidatesViewModel(viewModel: CandidatesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SetInfoViewModel::class)
    abstract fun bindSetInfoViewModel(viewModel: SetInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ConfirmClassViewModel::class)
    abstract fun bindConfirmClassViewModel(viewModel: ConfirmClassViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CandidateProfileViewModel::class)
    abstract fun bindCandidateProfileViewModel(viewModel: CandidateProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContributorsViewModel::class)
    abstract fun bindContributorsViewModel(viewModel: ContributorsViewModel): ViewModel
}
