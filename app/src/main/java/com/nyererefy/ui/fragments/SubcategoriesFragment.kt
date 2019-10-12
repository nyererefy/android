package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nyererefy.adapters.SubcategoriesAdapter
import com.nyererefy.databinding.FragmentSubcategoriesBinding
import com.nyererefy.di.Injectable
import com.nyererefy.ui.fragments.base.BaseFragment
import com.nyererefy.viewmodels.SubcategoriesViewModel
import javax.inject.Inject

class SubcategoriesFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SubcategoriesViewModel by viewModels { viewModelFactory }
    private val args by navArgs<SubcategoriesFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSubcategoriesBinding.inflate(inflater, container, false)
        val adapter = SubcategoriesAdapter()
        binding.recyclerView.adapter = adapter

        subscribeUI(adapter)
        return binding.root
    }

    private fun subscribeUI(adapter: SubcategoriesAdapter) {
        viewModel.setElectionId(args.electionId)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.subcategories())
        })
    }
}