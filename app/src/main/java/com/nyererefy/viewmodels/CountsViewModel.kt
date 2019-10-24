package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.CandidatesRepository
import javax.inject.Inject

class CountsViewModel
@Inject constructor(private val repository: CandidatesRepository) : ViewModel() {
    private val _subcategoryId = MutableLiveData<String>()

    private val _candidatesResource = Transformations.map(_subcategoryId) {
        repository.fetchCandidatesAndVotes(it)
    }

    val data = Transformations.switchMap(_candidatesResource) { it.data }
    val networkState = Transformations.switchMap(_candidatesResource) { it.networkState }

    fun setSubcategoryId(electionId: String) {
        if (_subcategoryId.value != electionId) {
            _subcategoryId.value = electionId
        }
    }

    fun retry() {
        _subcategoryId.value?.let {
            _subcategoryId.value = it
        }
    }
}
