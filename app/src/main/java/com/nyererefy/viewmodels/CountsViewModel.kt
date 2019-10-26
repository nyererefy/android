package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.CandidatesRepository
import javax.inject.Inject

class CountsViewModel
@Inject constructor(private val repository: CandidatesRepository) : ViewModel() {
    private val _subcategoryId = MutableLiveData<String>()
    private val _subcategoryIdForSub = MutableLiveData<String>()

    private val _queryResource = Transformations.map(_subcategoryId) {
        repository.fetchCandidatesAndVotes(it)
    }

    private val _subscriptionResource = Transformations.map(_subcategoryIdForSub) {
        repository.subscribeToCandidatesAndVotes(it)
    }

    val data = Transformations.switchMap(_queryResource) { it.data }
    val networkState = Transformations.switchMap(_queryResource) { it.networkState }
    val subData = Transformations.switchMap(_subscriptionResource) { it.data }
    val subState = Transformations.switchMap(_subscriptionResource) { it.subscriptionState }

    fun setSubcategoryId(electionId: String) {
        if (_subcategoryId.value != electionId) {
            _subcategoryId.value = electionId
        }
        if (_subcategoryIdForSub.value != electionId) {
            _subcategoryIdForSub.value = electionId
        }
    }

    fun retry() {
        _subcategoryId.value?.let {
            _subcategoryId.value = it
        }
    }

    fun reconnect() {
        _subcategoryIdForSub.value?.let {
            _subcategoryIdForSub.value = it
        }
    }
}
