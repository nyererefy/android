package com.konektedi.vs.motions

import android.arch.lifecycle.MutableLiveData
import com.konektedi.vs.utilities.api.ApiN
import com.konektedi.vs.utilities.api.getError
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Motion
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MotionsRepository {
    private val apiClient = ApiN.create()
    val mNetworkState = MutableLiveData<NetworkState>()

    fun getMotion(motionId: Int): MutableLiveData<Motion> {
        mNetworkState.value = NetworkState.LOADING

        val motion = MutableLiveData<Motion>()
        apiClient.getMotion(motionId).enqueue(
                object : Callback<Motion> {
                    override fun onFailure(call: Call<Motion>, t: Throwable) {
                        mNetworkState.value = NetworkState.serverMsg(t.message)
                    }

                    override fun onResponse(call: Call<Motion>, response: Response<Motion>) {
                        when {
                            response.isSuccessful -> {
                                mNetworkState.value = NetworkState.LOADED
                                motion.value = response.body()
                            }
                            else -> {
                                mNetworkState.value = NetworkState.serverMsg(getError(response as Response<ResponseBody>))
                            }
                        }
                    }
                })
        return motion
    }

    fun submit(map: Map<String, String>): MutableLiveData<NetworkState> {
        val call = apiClient.postOpinion(map)
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