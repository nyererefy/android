package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.CandidatesRepository
import com.nyererefy.graphql.type.CandidateEditInput
import javax.inject.Inject

class CandidateProfileViewModel
@Inject constructor(private val repository: CandidatesRepository) : ViewModel() {
    private val _candidateId = MutableLiveData<String>()
    private val _candidateEditInput = MutableLiveData<CandidateEditInput>()

    private val _queryResource = Transformations.map(_candidateId) {
        repository.fetchCandidate(it)
    }

    private val _subscriptionResource = Transformations.map(_candidateId) {
        repository.subscribeCandidate(it)
    }

    private val _inputResource = Transformations.map(_candidateEditInput) {
        repository.editCandidate(it)
    }

    val data = Transformations.switchMap(_queryResource) { it.data }
    val networkState = Transformations.switchMap(_queryResource) { it.networkState }
    val candidateEditState = Transformations.switchMap(_inputResource) { it.networkState }
    val subscriptionData = Transformations.switchMap(_subscriptionResource) { it.data }

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

    fun setCandidateEditInput(input: CandidateEditInput) {
        _candidateEditInput.value = input
    }
}
