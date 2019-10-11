package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.CategoriesRepository
import javax.inject.Inject

class CategoriesViewModel
@Inject constructor(private val repository: CategoriesRepository) : ViewModel() {
    private val _electionId = MutableLiveData<String>()

//    val categories: LiveData<Resource<CategoriesQuery.Data>>
//        get() {
//            return Transformations.switchMap(_electionId) {
//                repository.fetchCategories(it)
//            }
//        }

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