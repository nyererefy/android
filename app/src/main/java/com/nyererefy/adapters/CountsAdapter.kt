package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.nyererefy.R
import com.nyererefy.databinding.ListItemCountBinding
import com.nyererefy.utilities.common.BaseListAdapter
import com.nyererefy.utilities.model.CandidateAndVotesCount

class CountsAdapter(retryCallback: () -> Unit)
    : BaseListAdapter<CandidateAndVotesCount, ListItemCountBinding>(COMPARATOR, retryCallback) {

    override val layout = R.layout.list_item_count

    override fun createBinding(parent: ViewGroup): ListItemCountBinding {
        return ListItemCountBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ListItemCountBinding, item: CandidateAndVotesCount, position: Int) {
        binding.apply {
            this.candidate = item
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<CandidateAndVotesCount>() {
            override fun areContentsTheSame(old: CandidateAndVotesCount, new: CandidateAndVotesCount) =
                    old == new

            override fun areItemsTheSame(old: CandidateAndVotesCount, new: CandidateAndVotesCount) =
                    old.id == new.id
        }
    }
}