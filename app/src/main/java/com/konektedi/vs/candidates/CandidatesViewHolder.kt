package com.konektedi.vs.candidates

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.models.Candidate


class CandidatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nameView: TextView = view.findViewById(R.id.nameView)
    private val schoolView: TextView = view.findViewById(R.id.schoolView)
    private val voteBtn: Button = view.findViewById(R.id.voteBtn)
    private val cover: ImageView = view.findViewById(R.id.cover)
    private val radioButton: RadioButton = view.findViewById(R.id.radio_button)

    private var lastSelectedPosition = -1

    fun bind(candidate: Candidate?, mContext: Context, position: Int) {
        nameView.text = candidate!!.name
        schoolView.text = candidate.abbr

        radioButton.isChecked = position == lastSelectedPosition
        radioButton.setOnCheckedChangeListener(null)

        Glide.with(mContext)
                .load(candidate.cover)
                .apply(RequestOptions()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.holder)
                        .error(R.drawable.holder))
                .into(cover)

        if (candidate.participated == 1) {
            voteBtn.visibility = View.GONE
            (mContext as CandidatesActivity).showError(R.string.voted)

        } else voteBtn.visibility = View.VISIBLE

        if (candidate.opened == 0) {
            voteBtn.visibility = View.GONE
            (mContext as CandidatesActivity).showError(R.string.voting_disabled)
        }

        nameView.setOnClickListener { showProfile(candidate, mContext) }
        cover.setOnClickListener { showProfile(candidate, mContext) }
        voteBtn.setOnClickListener {
            (mContext as CandidatesActivity).confirmVoting(candidate)
        }

        radioButton.setOnClickListener {
            lastSelectedPosition = adapterPosition
            Toast.makeText(mContext, position.toString(), Toast.LENGTH_LONG).show()
            (mContext as CandidatesActivity).notifyAdapter()
        }
    }

    private fun showProfile(candidate: Candidate, mContext: Context) {
        val intent = Intent(mContext, Profile::class.java).apply {

            putExtra(Constants.COVER, candidate.cover)
            putExtra(Constants.NAME, candidate.name)
            putExtra(Constants.CLASS_NAME, candidate.abbr)

            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        mContext.startActivity(intent)
    }

    companion object {
        fun create(parent: ViewGroup): CandidatesViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.z_candidate, parent, false)
            return CandidatesViewHolder(view)
        }
    }
}