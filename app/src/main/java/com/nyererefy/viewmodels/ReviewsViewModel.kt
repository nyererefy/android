package com.nyererefy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.nyererefy.data.repositories.ReviewsRepository
import javax.inject.Inject

data class ReviewsArgs(val subcategoryId: Int, val offset: Int?)

class ReviewsViewModel
@Inject constructor(private val repository: ReviewsRepository) : ViewModel() {
    private val _args = MutableLiveData<ReviewsArgs>()

    private val _resource = Transformations.map(_args) {
        repository.fetchReviews(it)
    }

    val data = Transformations.switchMap(_resource) { it.data }
    val networkState = Transformations.switchMap(_resource) { it.networkState }

    fun setArgs(subcategoryId: Int, offset: Int? = null) {
        val args = ReviewsArgs(subcategoryId, offset)

        if (_args.value != args) {
            _args.value = args
        }
    }

    fun retry() {
        _args.value?.let {
            _args.value = it
        }
    }
}

