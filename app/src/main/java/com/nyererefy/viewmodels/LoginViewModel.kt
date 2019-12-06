package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.LoginRepository
import javax.inject.Inject

class LoginViewModel
@Inject constructor(private val repository: LoginRepository) : ViewModel() {
    private val _token = MutableLiveData<String>()

    private val resource = Transformations.map(_token) {
        repository.submitGoogleToken(it)
    }

    fun setToken(token: String) {
        _token.value = token
    }

    val data = Transformations.switchMap(resource) { it?.data }
    val networkState = Transformations.switchMap(resource) { it?.networkState }


}