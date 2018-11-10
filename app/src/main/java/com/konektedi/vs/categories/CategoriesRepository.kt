package com.konektedi.vs.categories

import android.arch.lifecycle.MutableLiveData
import com.konektedi.vs.utilities.api.ApiN
import com.konektedi.vs.utilities.api.getError
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Category
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesRepository {
    private val apiClient = ApiN.create()
    val mNetworkState = MutableLiveData<NetworkState>()

    fun getCategories(electionId: Int): MutableLiveData<List<Category>> {
        mNetworkState.value = NetworkState.LOADING
        val categoriesList = MutableLiveData<List<Category>>()

        apiClient.getCategories(electionId).enqueue(
                object : Callback<List<Category>> {
                    override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                        mNetworkState.value = NetworkState.serverMsg(t.message)
                    }

                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) =
                            when {
                                response.isSuccessful -> {
                                    mNetworkState.value = NetworkState.LOADED
                                    categoriesList.value = response.body()
                                }
                                else -> mNetworkState.value = NetworkState.serverMsg(getError(response as Response<ResponseBody>))
                            }
                })
        return categoriesList
    }
}