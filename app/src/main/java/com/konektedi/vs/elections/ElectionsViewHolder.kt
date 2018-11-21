package com.konektedi.vs.elections

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.models.Election

class ElectionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val electionView: TextView = view.findViewById(R.id.title_view)

    fun bind(election: Election?, mContext: Context) {
        electionView.text = election!!.electionTitle

        electionView.setOnClickListener {
            val intent = Intent(mContext, ElectionView::class.java)
            intent.putExtra(Constants.ELECTION_ID, election.electionId)
            intent.putExtra(Constants.ELECTION_TITLE, election.electionTitle)
            mContext.startActivity(intent)
        }
    }


    companion object {
        fun create(parent: ViewGroup): ElectionsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.z_election, parent, false)
            return ElectionsViewHolder(view)
        }
    }
}