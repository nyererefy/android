package com.konektedi.vs.reviews

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.konektedi.vs.R
import com.konektedi.vs.utilities.models.Review

class ReviewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nameView: TextView = view.findViewById(R.id.name_view)
    private val reviewView: TextView = view.findViewById(R.id.review_view)
    private val timeView: TextView = view.findViewById(R.id.time_view)

    fun bind(review: Review?, mContext: Context) {
        nameView.text = review!!.username
        reviewView.text = review.review
        timeView.text = review.time
    }

    companion object {
        fun create(parent: ViewGroup): ReviewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.z_review, parent, false)
            return ReviewsViewHolder(view)
        }
    }
}