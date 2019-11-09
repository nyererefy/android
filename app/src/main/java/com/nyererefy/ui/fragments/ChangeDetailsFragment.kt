package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.nyererefy.databinding.FragmentChangeDetailsBinding
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.viewmodels.ChangeDetailsViewModel
import javax.inject.Inject

class ChangeDetailsFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ChangeDetailsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentChangeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}
