package com.konektedi.vs.elections

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.getRandomColor
import com.konektedi.vs.utilities.models.Election

class ElectionsViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    private val electionView: TextView = view.findViewById(R.id.title_view)
    private val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)

    fun bind(election: Election?, mContext: Context) {
        electionView.text = election!!.electionTitle

        val color = getRandomColor(mContext)
        constraintLayout.setBackgroundColor(Color.parseColor(color))

        itemView.setOnClickListener {
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