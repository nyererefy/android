package com.konektedi.vs.home.candidates

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.konektedi.vs.R
import com.konektedi.vs.utilities.models.Candidate

/**
 * Created by Sy on b/14/2018.
 */

class CandidatesAdapter(private val mContext: Context, private val candidatesList: List<Candidate>?) :
        RecyclerView.Adapter<CandidatesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidatesViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.z_candidate, parent, false)

        return CandidatesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return candidatesList?.size!!
    }

    override fun onBindViewHolder(holder: CandidatesViewHolder, position: Int) {
        holder.bind(candidate = candidatesList?.get(position), mContext = mContext)
    }

}