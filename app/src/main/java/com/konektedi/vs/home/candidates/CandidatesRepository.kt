package com.konektedi.vs.home.candidates


import android.arch.lifecycle.MutableLiveData
import android.util.Log

import com.konektedi.vs.utilities.NetworkStatus
import com.konektedi.vs.utilities.api.ApiN
import com.konektedi.vs.utilities.api.ApiUtilities
import com.konektedi.vs.utilities.api.getError
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Candidate
import okhttp3.ResponseBody

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
                        mNetworkState.value = NetworkState.serverMsg(t.message) //TODO Weka failed to connect basi.
                    }

                    override fun onResponse(call: Call<List<Candidate>>, response: Response<List<Candidate>>) {
                        when {
                            response.isSuccessful -> {
                                mNetworkState.value = NetworkState.LOADED
                                categoriesList.value = response.body()
                            }
                            else -> {
                                mNetworkState.value = NetworkState.serverMsg(getError(response as Response<ResponseBody>))
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

