package com.nyererefy.viewmodels

import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.UserRepository
import javax.inject.Inject

class ChangeDetailsViewModel
@Inject constructor(private val repository: UserRepository) : ViewModel() {
}
