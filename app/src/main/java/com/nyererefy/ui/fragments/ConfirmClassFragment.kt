package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.nyererefy.databinding.FragmentConfirmClassBinding
import com.nyererefy.di.Injectable
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.viewmodels.ConfirmClassViewModel
import javax.inject.Inject

class ConfirmClassFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ConfirmClassViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentConfirmClassBinding.inflate(inflater, container, false)
        return binding.root
    }
}
