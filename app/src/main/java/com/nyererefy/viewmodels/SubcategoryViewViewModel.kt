package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel which use activity scope to handle this communication.
 */
class SubcategoryViewViewModel : ViewModel() {
    val subcategoryId = MutableLiveData<String>()
    val isLive = MutableLiveData<Boolean>()

    fun setSubcategoryId(subcategoryId: String) {
        this.subcategoryId.value = subcategoryId
    }

    fun setIsLive(isLive: Boolean) {
        this.isLive.value = isLive
    }
}
