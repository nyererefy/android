package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.ReviewsRepository
import com.nyererefy.graphql.type.ReviewInput
import javax.inject.Inject

class AddReviewViewModel
@Inject constructor(private val repository: ReviewsRepository) : ViewModel() {
    private val _input = MutableLiveData<ReviewInput>()
    val isBtnGone = MutableLiveData(true)

    private val _resource = Transformations.map(_input) {
        repository.postReview(it)
    }

    val data = Transformations.switchMap(_resource) { it.data }
    val networkState = Transformations.switchMap(_resource) { it.networkState }

    fun setInput(input: ReviewInput) {
        _input.value = input
    }

    fun retry() {
        _input.value?.let {
            _input.value = it
        }
    }

    fun inputChanged(text: String) {
        isBtnGone.value = text.isBlank()
    }
}
