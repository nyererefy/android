package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nyererefy.databinding.FragmentCandidateProfileBinding
import com.nyererefy.di.Injectable
import com.nyererefy.viewmodels.CandidateProfileViewModel
import javax.inject.Inject

//todo edit details using bottom sheet.
class CandidateProfileFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CandidateProfileViewModel by viewModels { viewModelFactory }
    private val args by navArgs<CandidateProfileFragmentArgs>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCandidateProfileBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.setCandidateId(args.candidateId)

        return binding.root
    }

}
