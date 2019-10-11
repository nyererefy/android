package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nyererefy.adapters.CategoriesAdapter
import com.nyererefy.databinding.CategoriesFragmentBinding
import com.nyererefy.di.Injectable
import com.nyererefy.ui.fragments.base.BaseFragment
import com.nyererefy.viewmodels.CategoriesViewModel
import javax.inject.Inject

class CategoriesFragment : BaseFragment(), Injectable {
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

//        viewModel.categories.observe(viewLifecycleOwner, Observer {
//            when (it.status) {
//                Status.LOADING -> {
//                }
//                Status.SUCCESS -> {
//                    adapter.submitList(it.elections?.categories())
//                }
//                Status.ERROR -> {
//                    longToast(it.message.toString())
//                }
//            }
//        })
    }
}