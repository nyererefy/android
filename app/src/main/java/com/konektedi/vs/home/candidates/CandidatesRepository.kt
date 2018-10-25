package com.konektedi.vs.home.candidates


import android.arch.lifecycle.MutableLiveData

import com.konektedi.vs.utilities.NetworkStatus
import com.konektedi.vs.utilities.api.ApiN
import com.konektedi.vs.utilities.api.ApiUtilities
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Candidate

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidatesRepository {
    private val apiClient = ApiN.create()
    val mNetworkState = MutableLiveData<NetworkState>()

    fun getCandidates(electionId: Int, categoryId: Int): MutableLiveData<List<Candidate>> {
        mNetworkState.value = NetworkState.LOADING
        val categoriesList = MutableLiveData<List<Candidate>>()

        apiClient.getCandidates(electionId, categoryId).enqueue(
                object : Callback<List<Candidate>> {
                    override fun onFailure(call: Call<List<Candidate>>, t: Throwable) {
                        mNetworkState.value = NetworkState.FAILED
                    }

                    override fun onResponse(call: Call<List<Candidate>>, response: Response<List<Candidate>>) {
                        when {
                            response.isSuccessful -> {
                                mNetworkState.value = NetworkState.LOADED
                                categoriesList.value = response.body()
                            }
                            response.code() == 404 -> mNetworkState.value = NetworkState.END
                            else -> {
                                mNetworkState.postValue(NetworkState.error("error code: ${response.code()}"))
                            }
                        }
                    }
                })
        return categoriesList
    }

    //TODO check
//    fun checkVote(electionId: Int, categoryId: Int): MutableLiveData<List<Candidate>> {
//        mNetworkState.value = NetworkState.LOADING
//
//        apiClient.getCandidates(electionId, categoryId).enqueue(
//                object : Callback<List<Candidate>> {
//                    override fun onFailure(call: Call<List<Candidate>>, t: Throwable) {
//                    }
//
//                    override fun onResponse(call: Call<List<Candidate>>, response: Response<List<Candidate>>) {
//
//                    }
//                })
//    }
}

