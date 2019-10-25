package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nyererefy.adapters.ReviewsAdapter
import com.nyererefy.databinding.FragmentReviewsBinding
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.utilities.model.Review
import com.nyererefy.utilities.model.User
import com.nyererefy.viewmodels.ReviewsViewModel
import com.nyererefy.viewmodels.SubcategoryViewViewModel
import javax.inject.Inject

class ReviewsFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ReviewsViewModel by viewModels { viewModelFactory }
    private lateinit var subcategoryViewViewModel: SubcategoryViewViewModel
    private lateinit var adapter: ReviewsAdapter
    private lateinit var binding: FragmentReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subcategoryViewViewModel = activity?.run {
            ViewModelProviders.of(this)[SubcategoryViewViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        binding = FragmentReviewsBinding.inflate(inflater, container, false)

        adapter = ReviewsAdapter { viewModel.retry() }

        binding.recyclerView.adapter = adapter
        subscribeUI()

        return binding.root
    }

    private fun subscribeUI() {
        viewModel.setArgs(subcategoryViewViewModel.subcategoryId.value!!.toInt())

        viewModel.data.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it.reviews().map { r ->
                val user = User(id = r.user().id(), avatar = r.user().avatar(), name = r.user().name())
                val review = Review(id = r.id(), content = r.content(), user = user)

                review
            })
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })

        viewModel.review.observe(viewLifecycleOwner, Observer {
            val currentList = adapter.currentList.toMutableList()
            val r = it.review()

            val user = User(id = r.user().id(), avatar = r.user().avatar(), name = r.user().name())
            val review = Review(id = r.id(), content = r.content(), user = user)
            currentList.add(review)

            adapter.submitList(currentList)

            //Scroll to bottom for user to see his/her review.
            if (pref.studentId == user.id) {
                binding.recyclerView.smoothScrollToPosition(currentList.size - 1)
            }
        })
    }

}
