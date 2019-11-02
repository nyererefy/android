package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.GithubRepository
import javax.inject.Inject

class ContributorsViewModel
@Inject constructor(private val repository: GithubRepository) : ViewModel() {
    private val _repo = MutableLiveData<String>()

    private val _resource = map(_repo) {
        repository.fetchContributors(it)
    }

    val data = switchMap(_resource) { it.data }
    val networkState = switchMap(_resource) { it.networkState }

    fun fetch() {
        //todo we can show contributors to other nyererefy's repos as well
        val repo = "nyererefy-android"

        if (_repo.value != repo) {
            _repo.value = repo
        }
    }

    fun retry() {
        _repo.value?.let {
            _repo.value = it
        }
    }
}
