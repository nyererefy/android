package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nyererefy.databinding.FragmentConfirmClassBinding
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.utilities.common.NetworkState
import com.nyererefy.viewmodels.ConfirmClassViewModel
import com.nyererefy.viewmodels.SetupActivityViewModel
import javax.inject.Inject

class ConfirmClassFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ConfirmClassViewModel by viewModels { viewModelFactory }
    private val setupViewModel: SetupActivityViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentConfirmClassBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetch()

        viewModel.confirmState.observe(viewLifecycleOwner, Observer {
            if (it === NetworkState.LOADED) {
                setupViewModel.hasConfirmed.value = true
            }
        })
    }

}
