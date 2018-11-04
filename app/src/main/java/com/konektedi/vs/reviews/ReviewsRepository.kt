package com.konektedi.vs.reviews

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.konektedi.vs.utilities.api.ApiN
import com.konektedi.vs.utilities.api.getError
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Result
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewsRepository {

    private val apiClient = ApiN.create()
    val mNetworkState = MutableLiveData<NetworkState>()

    fun getVotes(categoryId: Int): MutableLiveData<List<Result>> {
        mNetworkState.value = NetworkState.LOADING
        val votesList = MutableLiveData<List<Result>>()

        apiClient.getResult(categoryId).enqueue(
                object : Callback<List<Result>> {
                    override fun onFailure(call: Call<List<Result>>, t: Throwable) {
                        mNetworkState.value = NetworkState.error(t.message)
                    }

                    override fun onResponse(call: Call<List<Result>>, response: Response<List<Result>>) {
                        when {
                            response.isSuccessful -> {
                                mNetworkState.value = NetworkState.LOADED
                                votesList.value = response.body()
                            }
                            else -> mNetworkState.postValue(NetworkState.error(getError(response
                                    as Response<ResponseBody>)))
                        }
                    }
                })
        return votesList
    }


    fun postReview(map: Map<String, String>): MutableLiveData<NetworkState> {
        mNetworkState.value = NetworkState.LOADING

        apiClient.postReview(map).enqueue(
                object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        mNetworkState.value = NetworkState.FAILED
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) =
                            when {
                                response.isSuccessful -> mNetworkState.value = NetworkState.LOADED
                                else -> mNetworkState.value = NetworkState.error(response.message())
                            }
                })
        return mNetworkState
    }

}