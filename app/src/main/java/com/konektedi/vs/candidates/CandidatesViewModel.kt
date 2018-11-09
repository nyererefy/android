package com.konektedi.vs.candidates

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.CandidateProfile
import com.konektedi.vs.utilities.models.Listing


class CandidatesViewModel : ViewModel() {
    private val repository = CandidatesRepository()

    val networkState = repository.mNetworkState

    fun getCandidates(election_id: Int, category_id: Int): LiveData<Listing> {
        return repository.getCandidates(election_id, category_id)
    }

    fun getCandidate(election_id: Int): LiveData<CandidateProfile> {
        return repository.getCandidate(election_id)
    }

    fun submitVote(map: Map<String, String>): MutableLiveData<NetworkState> {
        return repository.vote(map)
    }
}




