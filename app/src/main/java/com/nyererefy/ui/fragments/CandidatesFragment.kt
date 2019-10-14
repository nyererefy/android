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
import com.nyererefy.ui.sheets.ConfirmVotingBottomSheetFragment
import com.nyererefy.utilities.CandidateCheckListener
import com.nyererefy.utilities.SpacesItemDecoration
import com.nyererefy.viewmodels.CandidatesViewModel
import javax.inject.Inject


class CandidatesFragment : BaseFragment(), Injectable, CandidateCheckListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CandidatesViewModel by viewModels { viewModelFactory }
    private val args by navArgs<CandidatesFragmentArgs>()
    private lateinit var adapter: CandidatesAdapter
    private lateinit var bottomSheetFragment: ConfirmVotingBottomSheetFragment


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCandidatesBinding.inflate(inflater, container, false)

        adapter = CandidatesAdapter(this) { viewModel.retry() }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(8))
        binding.fragment = this
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        subscribeUI(adapter)

        return binding.root
    }

    private fun subscribeUI(adapter: CandidatesAdapter) {
        viewModel.setSubcategoryId(args.subcategoryId)

        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.candidates())
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })
    }

    fun onVoteClicked() {
        val candidate = adapter.selectedCandidate

        bottomSheetFragment = ConfirmVotingBottomSheetFragment(candidate, this)

        bottomSheetFragment.show(this.requireActivity().supportFragmentManager)
    }

    override fun onCandidateChecked() {
        viewModel.isVoteBtnEnabled.value = true
    }

    override fun onCandidateConfirmed(candidateId: String, password: String) {
        bottomSheetFragment.showProgressBar()
        bottomSheetFragment.dismiss()

        //todo show server error by snckbr
    }
}