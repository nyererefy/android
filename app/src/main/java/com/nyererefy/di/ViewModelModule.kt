package com.nyererefy.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nyererefy.viewmodels.CategoriesViewModel
import com.nyererefy.viewmodels.ElectionsViewModel
import com.nyererefy.viewmodels.LoginViewModel
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
    @ViewModelKey(CategoriesViewModel::class)
    abstract fun bindCategoriesViewModel(viewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}
