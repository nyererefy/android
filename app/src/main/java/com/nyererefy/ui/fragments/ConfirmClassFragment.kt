package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nyererefy.databinding.FragmentConfirmClassBinding
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.viewmodels.ConfirmClassViewModel
import javax.inject.Inject

class ConfirmClassFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ConfirmClassViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentConfirmClassBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetch()
    }

    fun navigate() {
        val direction = ConfirmClassFragmentDirections.actionConfirmToSetup()
        findNavController().navigate(direction)
    }
}
