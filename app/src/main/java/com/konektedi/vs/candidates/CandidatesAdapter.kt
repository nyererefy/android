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

/**
 * Created by Sy on b/14/2018.
 */

class CandidatesAdapter(private val mContext: Context,
                        private val candidatesList: List<Candidate>) :
        RecyclerView.Adapter<CandidatesAdapter.CandidatesViewHolder>() {

    private var selectedPosition = -1
    private var selectedCandidate: Candidate? = null

    inner class CandidatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameView: TextView = view.findViewById(R.id.nameView)
        private val schoolView: TextView = view.findViewById(R.id.schoolView)
        private val cover: ImageView = view.findViewById(R.id.cover)
        private val checkbox: CheckBox = view.findViewById(R.id.checkbox)

        fun bind(candidate: Candidate?, mContext: Context) {
            nameView.text = candidate!!.name
            schoolView.text = candidate.abbr

            checkbox.isChecked = adapterPosition == selectedPosition

            Glide.with(mContext)
                    .load(candidate.cover)
                    .apply(RequestOptions()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .placeholder(R.drawable.holder)
                            .error(R.drawable.holder))
                    .into(cover)

            nameView.setOnClickListener { showProfile(candidate, mContext) }
            cover.setOnClickListener { showProfile(candidate, mContext) }

            checkbox.setOnClickListener {
                selectedPosition = adapterPosition
                selectedCandidate = candidatesList[selectedPosition]
                notifyDataSetChanged()
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
    }

    fun passSelectedCandidate(): Candidate? {
        return selectedCandidate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidatesViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.z_candidate, parent, false)

        return CandidatesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return candidatesList.size
    }

    override fun onBindViewHolder(holder: CandidatesViewHolder, position: Int) {
        holder.bind(candidatesList[position], mContext)
    }


}