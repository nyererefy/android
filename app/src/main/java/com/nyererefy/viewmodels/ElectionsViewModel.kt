package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.ElectionsRepository
import javax.inject.Inject

class ElectionsViewModel
@Inject constructor(repository: ElectionsRepository) : ViewModel() {
    private val _query = MutableLiveData<String>()

    private val results = map(_query) {
        repository.fetchElections(it)
    }

    val elections = switchMap(results) { it.pagedList }
    val networkState = switchMap(results) { it.networkState }
    val refreshState = switchMap(results) { it.refreshState }

    fun setQuery(query: String) {
        if (_query.value != query) {
            _query.value = query
        }
    }

    fun retry() {
        val listing = results.value
        listing?.retry?.invoke()
    }

    fun refresh() {
        val listing = results.value
        listing?.refresh?.invoke()
    }
}