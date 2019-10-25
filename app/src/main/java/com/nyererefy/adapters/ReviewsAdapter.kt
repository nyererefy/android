package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.nyererefy.R
import com.nyererefy.databinding.ListItemReviewBinding
import com.nyererefy.graphql.ReviewsQuery
import com.nyererefy.utilities.common.BaseListAdapter
import com.nyererefy.utilities.model.Review


class ReviewsAdapter(retryCallback: () -> Unit)
    : BaseListAdapter<Review, ListItemReviewBinding>(COMPARATOR, retryCallback) {

    override val layout = R.layout.list_item_review

    override fun createBinding(parent: ViewGroup): ListItemReviewBinding {
        return ListItemReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ListItemReviewBinding, item: Review, position: Int) {
        binding.apply {
            this.review = item
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Review>() {
            override fun areContentsTheSame(old: Review, new: Review) =
                    old == new

            override fun areItemsTheSame(old: Review, new: Review) =
                    old.id == new.id
        }
    }
}