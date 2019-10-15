package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.nyererefy.R
import com.nyererefy.databinding.ListItemCandidateBinding
import com.nyererefy.graphql.CandidatesQuery
import com.nyererefy.ui.fragments.CandidatesFragmentDirections
import com.nyererefy.utilities.CandidateCheckListener
import com.nyererefy.utilities.common.BaseListAdapter

class CandidatesAdapter(
        private val candidateCheckListener: CandidateCheckListener,
        retryCallback: () -> Unit
) : BaseListAdapter<CandidatesQuery.Candidate, ListItemCandidateBinding>(COMPARATOR, retryCallback) {
    private var selectedPosition = -1
    lateinit var selectedCandidate: CandidatesQuery.Candidate

    override val layout = R.layout.list_item_candidate

    override fun createBinding(parent: ViewGroup): ListItemCandidateBinding {
        return ListItemCandidateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ListItemCandidateBinding, item: CandidatesQuery.Candidate, position: Int) {
        binding.apply {
            this.candidate = item

            this.clickListener = View.OnClickListener {
                val direction = CandidatesFragmentDirections.actionCandidatesToCandidateProfileFragment(item.id())
                it.findNavController().navigate(direction)
            }

            this.checkbox.isChecked = position == selectedPosition

            this.onCheckListener = View.OnClickListener {
                selectedPosition = position
                selectedCandidate = item
                notifyDataSetChanged()

                candidateCheckListener.onCandidateChecked()
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<CandidatesQuery.Candidate>() {
            override fun areContentsTheSame(old: CandidatesQuery.Candidate, new: CandidatesQuery.Candidate) =
                    old == new

            override fun areItemsTheSame(old: CandidatesQuery.Candidate, new: CandidatesQuery.Candidate) =
                    old.id() == new.id()
        }
    }
}