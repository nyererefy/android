package com.konektedi.vs.categories

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.konektedi.vs.utilities.models.Category


class CategoriesViewModel : ViewModel() {
    private val repo = CategoriesRepository()

    val networkState = repo.mNetworkState

    fun getCategories(election_id: Int): MutableLiveData<List<Category>> {
        return repo.getCategories(election_id)
    }

}