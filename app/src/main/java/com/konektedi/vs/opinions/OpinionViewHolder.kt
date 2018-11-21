package com.konektedi.vs.opinions

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.konektedi.vs.R
import com.konektedi.vs.utilities.models.Opinion

class OpinionViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    private var nameView: TextView = view.findViewById(R.id.nameView)
    private val opinionView: TextView = view.findViewById(R.id.opinionView)
    private var timeView: TextView = view.findViewById(R.id.timeView)

    fun bind(review: Opinion?, mContext: Context) {
        nameView.text = review!!.username
        opinionView.text = review.opinion
        timeView.text = review.time
    }

    companion object {
        fun create(parent: ViewGroup): OpinionViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.z_opinion, parent, false)
            return OpinionViewHolder(view)
        }
    }
}