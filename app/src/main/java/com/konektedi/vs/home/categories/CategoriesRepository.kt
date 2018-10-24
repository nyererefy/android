package com.konektedi.vs.home.categories

import android.arch.lifecycle.MutableLiveData
import com.konektedi.vs.utilities.api.ApiN
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Category
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
                        mNetworkState.value = NetworkState.FAILED
                    }

                    override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
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


}