package com.nyererefy.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nyererefy.viewmodels.ElectionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ElectionsViewModel::class)
    abstract fun bindUserViewModel(userViewModel: ElectionsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
