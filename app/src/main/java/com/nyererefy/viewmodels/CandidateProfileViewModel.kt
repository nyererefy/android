package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.CandidatesRepository
import javax.inject.Inject

class CandidateProfileViewModel
@Inject constructor(private val repository: CandidatesRepository) : ViewModel() {
    private val _candidateId = MutableLiveData<String>()

    private val _resource = Transformations.map(_candidateId) {
        repository.fetchCandidate(it)
    }

    val data = Transformations.switchMap(_resource) { it.data }
    val networkState = Transformations.switchMap(_resource) { it.networkState }

    fun setCandidateId(candidateId: String) {
        if (_candidateId.value != candidateId) {
            _candidateId.value = candidateId
        }
    }

    fun retry() {
        _candidateId.value?.let {
            _candidateId.value = it
        }
    }
}
