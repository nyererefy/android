package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.UserRepository
import com.nyererefy.graphql.type.FirebaseTokenInput
import javax.inject.Inject

class MainActivityViewModel
@Inject constructor(private val repository: UserRepository) : ViewModel() {
    private val _input = MutableLiveData<FirebaseTokenInput>()

    private val results = Transformations.map(_input) {
        repository.sendFirebaseToken(it)
    }

    val data = Transformations.switchMap(results) { it.data }
    val networkState = Transformations.switchMap(results) { it.networkState }

    fun setInput(input: FirebaseTokenInput) {
        _input.value = input
    }
}
