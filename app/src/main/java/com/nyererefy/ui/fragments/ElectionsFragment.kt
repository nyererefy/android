package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nyererefy.adapters.ElectionsAdapter
import com.nyererefy.databinding.ElectionsFragmentBinding
import com.nyererefy.di.Injectable
import com.nyererefy.viewmodels.ElectionsViewModel

class ElectionsFragment : Fragment(), Injectable {
    private lateinit var viewModel: ElectionsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = ElectionsFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this).get(ElectionsViewModel::class.java)

        val adapter = ElectionsAdapter {} //retry here.
        binding.recyclerView.adapter = adapter
        subscribeUI(adapter)

        return binding.root
    }

    private fun subscribeUI(adapter: ElectionsAdapter) {
        viewModel.elections.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.networkState.observe(viewLifecycleOwner, Observer { adapter.setNetworkState(it) })
    }
}