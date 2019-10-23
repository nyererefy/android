package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nyererefy.R
import com.nyererefy.adapters.ElectionsAdapter
import com.nyererefy.databinding.ElectionsFragmentBinding
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.viewmodels.ElectionsViewModel
import javax.inject.Inject


class ElectionsFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ElectionsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = ElectionsFragmentBinding.inflate(inflater, container, false)

        val adapter = ElectionsAdapter { viewModel.retry() }
        binding.recyclerView.adapter = adapter
        subscribeUI(adapter)

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun subscribeUI(adapter: ElectionsAdapter) {
        viewModel.setQuery("") //todo for searching elections.

        viewModel.elections.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.elections, menu)
    }
}