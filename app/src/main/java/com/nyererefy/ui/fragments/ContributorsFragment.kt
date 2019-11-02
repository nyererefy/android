package com.nyererefy.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nyererefy.adapters.ContributorAdapter
import com.nyererefy.databinding.FragmentContributorsBinding
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.viewmodels.ContributorsViewModel
import javax.inject.Inject


class ContributorsFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ContributorsViewModel by viewModels { viewModelFactory }
    private lateinit var adapter: ContributorAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContributorsBinding.inflate(inflater, container, false)

        adapter = ContributorAdapter { viewModel.retry() }
        binding.recyclerView.adapter = adapter
        subscribeUI()

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.fetch()

        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.repository()?.collaborators()?.nodes())
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })
    }

}
