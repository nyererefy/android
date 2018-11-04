package com.konektedi.vs.candidates


import android.arch.lifecycle.MutableLiveData

import com.konektedi.vs.utilities.api.ApiN
import com.konektedi.vs.utilities.api.getError
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Candidate
import com.konektedi.vs.utilities.models.Listing
import okhttp3.ResponseBody

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CandidatesRepository {
    private val apiClient = ApiN.create()
    val mNetworkState = MutableLiveData<NetworkState>()

    fun getCandidates(electionId: Int, categoryId: Int): MutableLiveData<Listing> {
        mNetworkState.value = NetworkState.LOADING
        val categoriesList = MutableLiveData<Listing>()

        apiClient.getCandidates(electionId, categoryId).enqueue(
                object : Callback<Listing> {
                    override fun onFailure(call: Call<Listing>, t: Throwable) {
                        mNetworkState.value = NetworkState.serverMsg(t.message) //TODO Weka failed to connect basi.
                    }

                    override fun onResponse(call: Call<Listing>, response: Response<Listing>) {
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

    fun vote(map: Map<String, String>): MutableLiveData<NetworkState> {
        val call = apiClient.vote(map)
        mNetworkState.value = NetworkState.LOADING

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                when {
                    response.isSuccessful -> mNetworkState.value = NetworkState.LOADED
                    else -> mNetworkState.value = NetworkState.serverMsg(getError(response))
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mNetworkState.value = NetworkState.serverMsg(t.message)
            }
        })
        return mNetworkState
    }
}
