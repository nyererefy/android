package com.nyererefy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.R
import com.nyererefy.data.repositories.UserRepository
import com.nyererefy.graphql.type.UserSetupInput
import com.nyererefy.utilities.SetUpInfoFormState
import javax.inject.Inject

class SetInfoViewModel
@Inject constructor(private val repository: UserRepository) : ViewModel() {
    private val _input = MutableLiveData<UserSetupInput>()

    private val _formState = MutableLiveData<SetUpInfoFormState>()
    val formState: LiveData<SetUpInfoFormState> = _formState

    private val results = Transformations.map(_input) {
        repository.setupAccount(it)
    }

    val data = Transformations.switchMap(results) { it.data }
    val networkState = Transformations.switchMap(results) { it.networkState }

    fun setInput(input: UserSetupInput) {
        _input.value = input
    }

    fun retry() {
        _input.value?.let {
            _input.value = it
        }
    }

    fun dataChanged(name: String, username: String, password: String, confPassword: String) {
        if (!isUserNameValid(username)) {
            _formState.value = SetUpInfoFormState(usernameError = R.string.invalid_username)
        } else if (!isNameValid(name)) {
            _formState.value = SetUpInfoFormState(nameError = R.string.invalid_name)
        } else if (!isPasswordValid(password)) {
            _formState.value = SetUpInfoFormState(passwordError = R.string.invalid_password)
        } else if (!arePasswordsMatch(password, confPassword)) {
            _formState.value = SetUpInfoFormState(passwordError = R.string.password_not_match)
        } else {
            _formState.value = SetUpInfoFormState(isDataValid = true)
        }
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotBlank() && name.length < 20
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank() && username.length < 16
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length == 4
    }

    private fun arePasswordsMatch(password: String, confPassword: String): Boolean {
        return password == confPassword
    }
}
