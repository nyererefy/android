package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetupActivityViewModel : ViewModel() {
    val hasConfirmed = MutableLiveData(false)
}