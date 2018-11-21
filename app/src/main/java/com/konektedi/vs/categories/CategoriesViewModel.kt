package com.konektedi.vs.categories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.konektedi.vs.utilities.models.Category
import com.konektedi.vs.utilities.models.Stat


class CategoriesViewModel : ViewModel() {
    private val repo = CategoriesRepository()

    val networkState = repo.mNetworkState

    fun getCategories(election_id: Int): LiveData<List<Category>> {
        return repo.getCategories(election_id)
    }

    fun getStats(election_id: Int): LiveData<List<Stat>> {
        return repo.getStats(election_id)
    }

}