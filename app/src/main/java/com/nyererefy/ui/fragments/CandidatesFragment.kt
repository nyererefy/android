package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nyererefy.adapters.CandidatesAdapter
import com.nyererefy.databinding.FragmentCandidatesBinding
import com.nyererefy.di.Injectable
import com.nyererefy.ui.fragments.base.BaseFragment
import com.nyererefy.viewmodels.CandidatesViewModel
import javax.inject.Inject


class CandidatesFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CandidatesViewModel by viewModels { viewModelFactory }
    private val args by navArgs<CandidatesFragmentArgs>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCandidatesBinding.inflate(inflater, container, false)

        val adapter = CandidatesAdapter()
        binding.recyclerView.adapter = adapter
        subscribeUI(adapter)

        return binding.root
    }

    private fun subscribeUI(adapter: CandidatesAdapter) {
        viewModel.setSubcategoryId(args.subcategoryId)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.candidates())
        })
    }
}