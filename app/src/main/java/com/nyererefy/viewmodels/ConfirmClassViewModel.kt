package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.UserRepository
import javax.inject.Inject

class ConfirmClassViewModel
@Inject constructor(private val repository: UserRepository) : ViewModel() {
    private val _fetch = MutableLiveData<Boolean>()
    private val _confirm = MutableLiveData<Boolean>()


    private val _resource = Transformations.map(_fetch) {
        repository.fetchClassInfo()
    }
    val info = Transformations.switchMap(_resource) { it.data }
    val infoState = Transformations.switchMap(_resource) { it.networkState }


    private val _result = Transformations.map(_confirm) {
        repository.confirmData()
    }

    val confirmState = Transformations.switchMap(_result) { it.networkState }

    fun fetch() {
        _fetch.value = true
    }

    fun confirm() {
        _confirm.value = true
    }
}

//todo redo this.

