package com.nyererefy.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.nyererefy.R
import com.nyererefy.viewmodels.AddReviewViewModel

class AddReviewFragment : Fragment() {

    companion object {
        fun newInstance() = AddReviewFragment()
    }

    private lateinit var viewModel: AddReviewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
