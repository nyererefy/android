package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nyererefy.adapters.CategoriesAdapter
import com.nyererefy.databinding.CategoriesFragmentBinding
import com.nyererefy.di.Injectable
import com.nyererefy.utilities.Status
import com.nyererefy.viewmodels.CategoriesViewModel
import org.jetbrains.anko.support.v4.longToast
import javax.inject.Inject

class CategoriesFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CategoriesViewModel by viewModels { viewModelFactory }
    private val args by navArgs<CategoriesFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = CategoriesFragmentBinding.inflate(inflater, container, false)
        val adapter = CategoriesAdapter()
        binding.recyclerView.adapter = adapter

        subscribeUI(adapter)
        return binding.root
    }

    private fun subscribeUI(adapter: CategoriesAdapter) {
        viewModel.setElectionId(args.electionId)

        viewModel.categories.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                    adapter.submitList(it.data?.categories())
                }
                Status.ERROR -> {
                    longToast(it.message.toString())
                }
            }
        })
    }
}