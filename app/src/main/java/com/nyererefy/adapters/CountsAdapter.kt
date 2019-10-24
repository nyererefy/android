package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.nyererefy.R
import com.nyererefy.databinding.ListItemCountBinding
import com.nyererefy.graphql.CountsQuery.CandidatesAndVotesCount
import com.nyererefy.utilities.common.BaseListAdapter

class CountsAdapter(retryCallback: () -> Unit)
    : BaseListAdapter<CandidatesAndVotesCount, ListItemCountBinding>(COMPARATOR, retryCallback) {

    override val layout = R.layout.list_item_count

    override fun createBinding(parent: ViewGroup): ListItemCountBinding {
        return ListItemCountBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ListItemCountBinding, item: CandidatesAndVotesCount, position: Int) {
        binding.apply {
            this.candidate = item
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<CandidatesAndVotesCount>() {
            override fun areContentsTheSame(old: CandidatesAndVotesCount, new: CandidatesAndVotesCount) =
                    old == new

            override fun areItemsTheSame(old: CandidatesAndVotesCount, new: CandidatesAndVotesCount) =
                    old.id() == new.id()
        }
    }
}