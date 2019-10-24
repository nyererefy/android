package com.nyererefy.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.nyererefy.R
import com.nyererefy.databinding.FragmentSubcategoryViewBinding
import com.nyererefy.utilities.common.ViewPagerAdapter
import com.nyererefy.viewmodels.SubcategoryViewViewModel

class SubcategoryViewFragment : Fragment() {

    private lateinit var binding: FragmentSubcategoryViewBinding
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewModel: SubcategoryViewViewModel
    private val args by navArgs<SubcategoryViewFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this)[SubcategoryViewViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        viewModel.setSubcategoryId(args.subcategoryId)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubcategoryViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        pagerAdapter = ViewPagerAdapter(childFragmentManager)

        pagerAdapter.addFragment(CandidatesFragment(), getString(R.string.candidates))
        pagerAdapter.addFragment(CountsAndReviewsFragment(), getString(R.string.votes_and_reviews))
        binding.viewPager.adapter = pagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}
