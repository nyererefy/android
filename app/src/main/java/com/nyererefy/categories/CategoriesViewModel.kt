package com.nyererefy.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nyererefy.utilities.models.Category
import com.nyererefy.utilities.models.Stat


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