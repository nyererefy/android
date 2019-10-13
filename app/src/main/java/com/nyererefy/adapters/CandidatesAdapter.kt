package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.nyererefy.databinding.ListItemCandidateBinding
import com.nyererefy.graphql.CandidatesQuery
import com.nyererefy.utilities.common.BaseListAdapter

class CandidatesAdapter : BaseListAdapter<CandidatesQuery.Candidate, ListItemCandidateBinding>(COMPARATOR) {
    override fun createBinding(parent: ViewGroup): ListItemCandidateBinding {
        return ListItemCandidateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ListItemCandidateBinding, item: CandidatesQuery.Candidate) {
        binding.candidate = item
        binding.clickListener = View.OnClickListener {
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