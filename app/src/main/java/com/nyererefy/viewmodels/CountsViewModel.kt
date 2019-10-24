package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.CandidatesRepository
import javax.inject.Inject

class CountsViewModel
@Inject constructor(private val repository: CandidatesRepository) : ViewModel() {
    private val _subcategoryId = MutableLiveData<String>()

    private val _countsQueryResource = Transformations.map(_subcategoryId) {
        repository.fetchCandidatesAndVotes(it)
    }

    private val _countsSubscriptionResource = Transformations.map(_subcategoryId) {
        repository.subscribeToCandidatesAndVotes(it)
    }

    val data = Transformations.switchMap(_countsQueryResource) { it.data }
    val networkState = Transformations.switchMap(_countsQueryResource) { it.networkState }
    val subscriptionData = Transformations.switchMap(_countsSubscriptionResource) { it.data }


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
