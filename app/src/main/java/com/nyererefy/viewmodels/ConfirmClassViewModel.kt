package com.nyererefy.viewmodels

import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.UserRepository
import javax.inject.Inject

class ConfirmClassViewModel
@Inject constructor(repository: UserRepository) : ViewModel() {
    val networkState = repository.confirmData()
}
