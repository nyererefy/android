package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.nyererefy.R
import com.nyererefy.databinding.ListItemReviewBinding
import com.nyererefy.graphql.ReviewsQuery
import com.nyererefy.utilities.common.BaseListAdapter


class ReviewsAdapter(retryCallback: () -> Unit)
    : BaseListAdapter<ReviewsQuery.Review, ListItemReviewBinding>(COMPARATOR, retryCallback) {

    override val layout = R.layout.list_item_review

    override fun createBinding(parent: ViewGroup): ListItemReviewBinding {
        return ListItemReviewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ListItemReviewBinding, item: ReviewsQuery.Review, position: Int) {
        binding.apply {
            this.review = item
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<ReviewsQuery.Review>() {
            override fun areContentsTheSame(old: ReviewsQuery.Review, new: ReviewsQuery.Review) =
                    old == new

            override fun areItemsTheSame(old: ReviewsQuery.Review, new: ReviewsQuery.Review) =
                    old.id() == new.id()
        }
    }
}