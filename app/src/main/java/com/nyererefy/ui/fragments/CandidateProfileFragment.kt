package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nyererefy.R
import com.nyererefy.databinding.FragmentCandidateProfileBinding
import com.nyererefy.di.Injectable
import com.nyererefy.graphql.type.CandidateEditInput
import com.nyererefy.ui.sheets.EditBioBottomSheetFragment
import com.nyererefy.utilities.BioListener
import com.nyererefy.utilities.Pref
import com.nyererefy.utilities.common.Constants.NYEREREFY_URL
import com.nyererefy.utilities.common.NetworkState
import com.nyererefy.viewmodels.CandidateProfileViewModel
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.share
import javax.inject.Inject

class CandidateProfileFragment : Fragment(), Injectable, BioListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CandidateProfileViewModel by viewModels { viewModelFactory }
    private val args by navArgs<CandidateProfileFragmentArgs>()
    private lateinit var sheetFragment: EditBioBottomSheetFragment
    private lateinit var binding: FragmentCandidateProfileBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCandidateProfileBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.setCandidateId(args.candidateId)

        viewModel.subscriptionData.observe(viewLifecycleOwner, Observer {
            binding.bio.text = it.candidate().bio()
            longToast(getString(R.string.bio_updated))
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    fun openSheet(bio: String?) {
        sheetFragment = EditBioBottomSheetFragment.newInstance()
        sheetFragment.setBio(bio)
        sheetFragment.setListener(this)
        sheetFragment.show(requireActivity().supportFragmentManager, tag)
    }

    override fun onSaveClicked(bio: String) {
        val input = CandidateEditInput.builder().bio(bio).id(args.candidateId.toInt()).build()

        viewModel.submitBio(input).observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.LOADING -> sheetFragment.showProgressBar()
                else -> {
                    sheetFragment.dismissAllowingStateLoss()

                    it.msg?.run {
                        binding.swipeRefreshLayout.indefiniteSnackbar(
                                this,
                                getString(R.string.dismiss)
                        ) {}
                    }
                }
            }
        })
    }

    fun getUserId(): String? {
        val pref = Pref(requireContext())
        return pref.studentId
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
