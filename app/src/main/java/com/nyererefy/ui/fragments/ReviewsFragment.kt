package com.nyererefy.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nyererefy.R
import com.nyererefy.viewmodels.ReviewsViewModel

class ReviewsFragment : Fragment() {

    companion object {
        fun newInstance() = ReviewsFragment()
    }

    private lateinit var viewModel: ReviewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ReviewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
