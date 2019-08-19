package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nyererefy.databinding.ElectionsFragmentBinding
import com.nyererefy.di.Injectable
import com.nyererefy.utilities.Status
import com.nyererefy.viewmodels.ElectionsViewModel
import org.jetbrains.anko.support.v4.longToast
import timber.log.Timber
import javax.inject.Inject

class ElectionsFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ElectionsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = ElectionsFragmentBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ElectionsViewModel::class.java)

        //val adapter = ElectionsAdapter {} //retry here.
        //binding.recyclerView.adapter = adapter
        //subscribeUI(adapter)
        subscribeUI()

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.getElections().observe(viewLifecycleOwner, Observer {
            Timber.d(it.data.toString())
            when (it.status) {
                Status.LOADING -> {
                }
                Status.SUCCESS -> {
                }
                Status.ERROR -> {
                    longToast(it.message.orEmpty())
                }
            }
        })
    }
}