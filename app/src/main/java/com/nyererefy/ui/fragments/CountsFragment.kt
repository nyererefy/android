package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nyererefy.adapters.CountsAdapter
import com.nyererefy.databinding.FragmentCountsBinding
import com.nyererefy.utilities.SpacesItemDecoration
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.utilities.model.CandidateAndVotesCount
import com.nyererefy.utilities.model.CandidateUserProfile
import com.nyererefy.viewmodels.CountsViewModel
import com.nyererefy.viewmodels.SubcategoryViewViewModel
import javax.inject.Inject

class CountsFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CountsViewModel by activityViewModels { viewModelFactory }
    private lateinit var binding: FragmentCountsBinding
    private lateinit var adapter: CountsAdapter
    private lateinit var subcategoryViewViewModel: SubcategoryViewViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subcategoryViewViewModel = activity?.run {
            ViewModelProviders.of(this)[SubcategoryViewViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        subcategoryViewViewModel.subcategoryId.observe(this, Observer {
            viewModel.setSubcategoryId(it)
        })

        subcategoryViewViewModel.isLive.observe(this, Observer {
            viewModel.isLive.value = it
            if (it) subscribeUI()
        })
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCountsBinding.inflate(inflater, container, false)

        adapter = CountsAdapter { viewModel.retry() }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(SpacesItemDecoration(8))
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.data.observe(this, Observer {
            adapter.submitList(it.candidatesAndVotesCount().map { c ->
                CandidateAndVotesCount(
                        id = c.id(),
                        votesCount = c.votesCount(),
                        avatar = c.avatar(),
                        user = CandidateUserProfile(c.user().name())
                )
            })
        })

        viewModel.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })

        viewModel.subData.observe(this, Observer {
            val c = it.candidateAndVotesCount()

            val newCount = CandidateAndVotesCount(
                    id = c.id(),
                    votesCount = c.votesCount(),
                    avatar = c.avatar(),
                    user = CandidateUserProfile(c.user().name())
            )

            val currentList = adapter.currentList.toMutableList()
            val currentCount = currentList.find { i -> i.id == c.id() }

            //Previous count need to be replaced here since DiffUtil seems like useless to me for this.
            currentList[currentList.indexOf(currentCount)] = newCount

            //Submitting new list.
            adapter.submitList(currentList)
        })
    }

}
