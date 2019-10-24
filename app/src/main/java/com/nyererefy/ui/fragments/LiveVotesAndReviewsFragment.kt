package com.nyererefy.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nyererefy.R
import com.nyererefy.viewmodels.LiveVotesAndReviewsViewModel

class LiveVotesAndReviewsFragment : Fragment() {

    companion object {
        fun newInstance() = LiveVotesAndReviewsFragment()
    }

    private lateinit var viewModel: LiveVotesAndReviewsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.live_votes_and_reviews_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LiveVotesAndReviewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
