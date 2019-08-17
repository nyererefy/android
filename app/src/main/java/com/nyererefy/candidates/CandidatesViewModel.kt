package com.nyererefy.candidates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nyererefy.utilities.common.NetworkState
import com.nyererefy.utilities.models.CandidateProfile
import com.nyererefy.utilities.models.Listing
import okhttp3.RequestBody


class CandidatesViewModel : ViewModel() {
    private val repository = CandidatesRepository()

    val networkState = repository.mNetworkState

    fun getCandidates(election_id: Int, category_id: Int): LiveData<Listing> {
        return repository.getCandidates(election_id, category_id)
    }

    fun getCandidate(candidate_id: Int): LiveData<CandidateProfile> {
        return repository.getCandidate(candidate_id)
    }

    fun submitVote(map: Map<String, String>): LiveData<NetworkState> {
        return repository.vote(map)
    }

    fun updateBiography(map: Map<String, String>): LiveData<NetworkState> {
        return repository.updateBiography(map)
    }

    fun uploadCover(requestBody: RequestBody): LiveData<NetworkState> {
        return repository.uploadCover(requestBody)
    }
}




