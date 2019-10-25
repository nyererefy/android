package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nyererefy.databinding.FragmentAddReviewBinding
import com.nyererefy.graphql.type.ReviewInput
import com.nyererefy.utilities.afterTextChanged
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.viewmodels.AddReviewViewModel
import com.nyererefy.viewmodels.SubcategoryViewViewModel
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject

class AddReviewFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: AddReviewViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentAddReviewBinding
    private lateinit var subcategoryViewViewModel: SubcategoryViewViewModel


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
        binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeInput()

        return binding.root
    }

    private fun observeInput() {
        binding.editText.afterTextChanged {
            viewModel.inputChanged(it)
        }
    }

    fun onSendClicked() {
        val input = ReviewInput.builder()
                .content(binding.editText.text.toString())
                .subcategoryId(subcategoryViewViewModel.subcategoryId.value?.toInt()!!)
                .build()

        viewModel.setInput(input)
    }

}
