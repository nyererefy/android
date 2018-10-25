package com.konektedi.vs.home.candidates

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.konektedi.vs.utilities.models.Candidate


class CandidatesViewModel : ViewModel() {
    private val repository = CandidatesRepository()

    val networkState = repository.mNetworkState

    fun getCandidates(election_id: Int, category_id: Int): LiveData<List<Candidate>>? {
        return repository.getCandidates(election_id, category_id)
    }
}




