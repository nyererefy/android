package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nyererefy.R
import com.nyererefy.databinding.FragmentCandidateProfileBinding
import com.nyererefy.di.Injectable
import com.nyererefy.utilities.common.Constants.NYEREREFY_URL
import com.nyererefy.viewmodels.CandidateProfileViewModel
import org.jetbrains.anko.support.v4.share
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

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.candidate_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> share("$NYEREREFY_URL/candidate/${args.candidateId}")
        }
        return super.onOptionsItemSelected(item)
    }

}
