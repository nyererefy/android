package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.SubcategoriesRepository
import javax.inject.Inject

class SubcategoriesViewModel
@Inject constructor(private val repository: SubcategoriesRepository) : ViewModel() {
    private val _electionId = MutableLiveData<String>()

    private val _resource = Transformations.map(_electionId) {
        repository.fetchCategories(it)
    }

    val data = Transformations.switchMap(_resource) { it.data }
    val networkState = Transformations.switchMap(_resource) { it.networkState }

    fun setElectionId(electionId: String) {
        if (_electionId.value != electionId) {
            _electionId.value = electionId
        }
    }

    fun retry() {
        _electionId.value?.let {
            _electionId.value = it
        }
    }
}